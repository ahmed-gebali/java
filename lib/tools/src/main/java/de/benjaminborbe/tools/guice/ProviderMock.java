package de.benjaminborbe.tools.guice;

import com.google.inject.Provider;

import java.lang.reflect.Constructor;

public class ProviderMock<T> implements Provider<T> {

	private final Class<T> clazz;

	public ProviderMock(final Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T get() {
		try {
			final Constructor<T> c;
			c = clazz.getDeclaredConstructor();
			return c.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
