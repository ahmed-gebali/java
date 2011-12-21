package de.benjaminborbe.tools.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RegistryImpl<T> implements Registry<T> {

	private final Set<T> objects = new HashSet<T>();

	@Override
	public void add(final T object) {
		objects.add(object);
	}

	@Override
	public void remove(final T object) {
		objects.remove(object);
	}

	@Override
	public Collection<T> getAll() {
		return objects;
	}

}
