package de.benjaminborbe.storage.util;

import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class StorageConnectionPoolImpl implements StorageConnectionPool {

	private final boolean aliveCheck;

	private final StorageConfig storageConfig;

	private final Logger logger;

	private final BlockingQueue<StorageConnection> freeConnections;

	private final BlockingQueue<StorageConnection> allConnections;

	private final int maxConnections;

	private final int socketTimeout;

	@Inject
	public StorageConnectionPoolImpl(final Logger logger, final StorageConfig storageConfig) {
		this.logger = logger;
		this.storageConfig = storageConfig;
		this.maxConnections = storageConfig.getMaxConnections();
		this.socketTimeout = storageConfig.getSocketTimeout();
		this.aliveCheck = storageConfig.getAliveCheck();
		this.freeConnections = new LinkedBlockingQueue<StorageConnection>(maxConnections);
		this.allConnections = new LinkedBlockingQueue<StorageConnection>(maxConnections);
	}

	@Override
	public StorageConnection getConnection() throws StorageConnectionPoolException {
		try {
			if (!freeConnections.isEmpty()) {
				final StorageConnection connection = freeConnections.take();
				if (!aliveCheck || isAlive(connection)) {
					return connection;
				}
				else {
					closeConnection(connection);
					return getConnection();
				}
			}
			else if (allConnections.size() == maxConnections) {
				throw new StorageConnectionPoolException("max connections reached");
			}
			else {
				final StorageConnection c = createNewConnection();
				allConnections.offer(c);
				return c;
			}
		}
		catch (final TTransportException e) {
			throw new StorageConnectionPoolException(e);
		}
		catch (final SocketException e) {
			throw new StorageConnectionPoolException(e);
		}
		catch (final InterruptedException e) {
			throw new StorageConnectionPoolException(e);
		}
	}

	private boolean isAlive(final StorageConnection connection) {
		try {
			if (!connection.getTr().isOpen()) {
				return false;
			}
			final String keyspace = connection.getKeyspace();
			connection.getClient().set_keyspace(keyspace != null ? keyspace : "system");
			return true;
		}
		catch (final Exception e) {
			return false;
		}
	}

	private void closeConnection(final StorageConnection connection) {
		allConnections.remove(connection);
	}

	@Override
	public void releaseConnection(final StorageConnection connection) {
		if (connection == null) {
			logger.info("can't release connection null");
		}
		else {
			freeConnections.offer(connection);
		}
	}

	@Override
	public void close() {
		while (!allConnections.isEmpty()) {
			try {
				final StorageConnection connection = allConnections.take();
				connection.getTr().close();
			}
			catch (final InterruptedException e) {
			}
		}
		allConnections.clear();
		freeConnections.clear();
	}

	private StorageConnection createNewConnection() throws TTransportException, SocketException {
		logger.trace("createNewConnection to " + storageConfig.getHost() + ":" + storageConfig.getPort());
		final TSocket socket = new TSocket(storageConfig.getHost(), storageConfig.getPort());
		socket.setTimeout(socketTimeout);
		// socket.getSocket().setReuseAddress(true);
		socket.getSocket().setSoLinger(true, 0);

		final TFramedTransport tr = new TFramedTransport(socket);
		tr.open();
		final TProtocol protocol = new TBinaryProtocol(tr);
		final Client client = new Cassandra.Client(protocol);

		return new StorageConnection(tr, client);
	}

	@Override
	public int getFreeConnections() {
		return freeConnections.size();
	}

	@Override
	public int getConnections() {
		return allConnections.size();
	}

	@Override
	public int getMaxConnections() {
		return maxConnections;
	}
}
