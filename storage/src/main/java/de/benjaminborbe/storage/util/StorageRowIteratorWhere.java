package de.benjaminborbe.storage.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.apache.cassandra.thrift.Cassandra.Iface;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.IndexExpression;
import org.apache.cassandra.thrift.IndexOperator;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;

import de.benjaminborbe.storage.api.StorageException;
import de.benjaminborbe.storage.api.StorageRow;
import de.benjaminborbe.storage.api.StorageRowIterator;

public class StorageRowIteratorWhere implements StorageRowIterator {

	// COUNT > 1
	private static final int COUNT = 100;

	private final ColumnParent column_parent;

	private final IndexClause index_clause;

	private final SlicePredicate predicate;

	private List<KeySlice> cols;

	private int currentPos;

	private final String encoding;

	private final StorageConnectionPool storageConnectionPool;

	private final String keySpace;

	public StorageRowIteratorWhere(
			final StorageConnectionPool storageConnectionPool,
			final String keySpace,
			final String columnFamily,
			final String encoding,
			final List<String> columnNames,
			final Map<String, String> where) throws UnsupportedEncodingException {
		this.storageConnectionPool = storageConnectionPool;
		this.keySpace = keySpace;
		this.column_parent = new ColumnParent(columnFamily);
		this.encoding = encoding;

		index_clause = new IndexClause();
		for (final Entry<String, String> e : where.entrySet()) {

			final ByteBuffer column_name = ByteBuffer.wrap(e.getKey().getBytes(encoding));
			final IndexOperator op = IndexOperator.EQ;
			final ByteBuffer value = ByteBuffer.wrap(e.getValue().getBytes(encoding));
			final IndexExpression indexExpression = new IndexExpression(column_name, op, value);
			index_clause.addToExpressions(indexExpression);
		}

		index_clause.setStart_key(new byte[0]);
		index_clause.setCount(COUNT);

		final SlicePredicate predicate = new SlicePredicate();
		predicate.setColumn_names(buildColumnNames(columnNames));

		this.predicate = predicate;
	}

	@Override
	public boolean hasNext() throws StorageException {

		StorageConnection connection = null;
		try {
			connection = storageConnectionPool.getConnection();
			final Iface client = connection.getClient(keySpace);

			if (cols == null) {
				cols = client.get_indexed_slices(column_parent, index_clause, predicate, ConsistencyLevel.ONE);
				currentPos = 0;
			}
			else if (currentPos == cols.size()) {
				cols = client.get_indexed_slices(column_parent, index_clause, predicate, ConsistencyLevel.ONE);
				currentPos = 1;
			}
			return currentPos < cols.size();
		}
		catch (final InvalidRequestException e) {
			throw new StorageException(e);
		}
		catch (final UnavailableException e) {
			throw new StorageException(e);
		}
		catch (final TimedOutException e) {
			throw new StorageException(e);
		}
		catch (final TException e) {
			throw new StorageException(e);
		}
		catch (final StorageConnectionPoolException e) {
			throw new StorageException(e);
		}
		finally {
			storageConnectionPool.releaseConnection(connection);
		}
	}

	private List<ByteBuffer> buildColumnNames(final List<String> columnNames) throws UnsupportedEncodingException {
		final List<ByteBuffer> result = new ArrayList<ByteBuffer>();
		for (final String columnName : columnNames) {
			result.add(ByteBuffer.wrap(columnName.getBytes(encoding)));
		}
		return result;
	}

	@Override
	public StorageRow next() throws StorageException {
		try {
			if (hasNext()) {
				final KeySlice keySlice = cols.get(currentPos);
				index_clause.setStart_key(keySlice.getKey());
				final Map<String, byte[]> data = new HashMap<String, byte[]>();
				for (final ColumnOrSuperColumn c : keySlice.getColumns()) {
					final Column column = c.getColumn();
					data.put(new String(column.getName(), encoding), column.getValue());
				}
				currentPos++;
				return new StorageRowImpl(encoding, keySlice.getKey(), data);
			}
			else {
				throw new NoSuchElementException();
			}
		}
		catch (final UnsupportedEncodingException e) {
			throw new StorageException(e);
		}
	}
}