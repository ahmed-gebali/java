package de.benjaminborbe.util.math;

public class AddExpression extends Expression {

	public AddExpression(final HasValue valueA, final HasValue valueB) {
		super(valueA, valueB);
	}

	@Override
	public long getValue() {
		return getValueA().getValue() + getValueB().getValue();
	}

	@Override
	public String toString() {
		return getValueA().getValue() + " + " + getValueB().getValue();
	}
}
