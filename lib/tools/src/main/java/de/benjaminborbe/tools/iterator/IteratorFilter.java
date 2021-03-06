package de.benjaminborbe.tools.iterator;

import com.google.common.base.Predicate;
import de.benjaminborbe.api.IteratorWithException;

import java.util.NoSuchElementException;

public class IteratorFilter<T, E extends Exception> implements IteratorWithException<T, E> {

	private final IteratorWithException<T, E> entityIterator;

	private final Predicate<T> predicate;

	private T next;

	public IteratorFilter(final IteratorWithException<T, E> entityIterator, final Predicate<T> predicate) {
		this.entityIterator = entityIterator;
		this.predicate = predicate;
	}

	@Override
	public boolean hasNext() throws E {
		if (next != null) {
			return true;
		}
		while (entityIterator.hasNext()) {
			final T e = entityIterator.next();
			if (predicate.apply(e)) {
				next = e;
				return true;
			}
		}
		return false;
	}

	@Override
	public T next() throws E {
		if (hasNext()) {
			final T result = next;
			next = null;
			return result;
		} else {
			throw new NoSuchElementException();
		}
	}
}
