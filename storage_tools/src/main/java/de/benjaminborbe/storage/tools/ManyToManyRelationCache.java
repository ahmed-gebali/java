package de.benjaminborbe.storage.tools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;

import de.benjaminborbe.api.Identifier;
import de.benjaminborbe.storage.api.StorageException;

public class ManyToManyRelationCache<A extends Identifier<?>, B extends Identifier<?>> implements ManyToManyRelation<A, B> {

	private final Map<String, Boolean> data = new HashMap<String, Boolean>();

	@Inject
	public ManyToManyRelationCache() {
		super();
	}

	@Override
	public void add(final A identifierA, final B identifierB) {
		data.put(identifierA + "-" + identifierB, Boolean.TRUE);
	}

	@Override
	public void remove(final A identifierA, final B identifierB) {
		data.remove(identifierA + "-" + identifierB);
	}

	@Override
	public boolean exists(final A identifierA, final B identifierB) {
		return data.containsKey(identifierA + "-" + identifierB) && Boolean.TRUE.equals(data.get(identifierA + "-" + identifierB));
	}

	@Override
	public void removeB(final B identifierB) throws StorageException {
		final Set<String> remove = new HashSet<String>();
		for (final String key : data.keySet()) {
			if (key.indexOf("-" + identifierB) != -1) {
				remove.add(key);
			}
		}
		for (final String key : remove) {
			data.remove(key);
		}
	}

	@Override
	public void removeA(final A identifierA) throws StorageException {
		final Set<String> remove = new HashSet<String>();
		for (final String key : data.keySet()) {
			if (key.indexOf(identifierA + "-") != -1) {
				remove.add(key);
			}
		}
		for (final String key : remove) {
			data.remove(key);
		}
	}

}
