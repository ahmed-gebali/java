package de.benjaminborbe.tools.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;

import de.benjaminborbe.api.ValidationError;
import de.benjaminborbe.api.ValidationErrorSimple;
import de.benjaminborbe.tools.validation.constraint.ValidationConstraint;

public class ValidationConstraintValidator {

	@Inject
	public ValidationConstraintValidator() {
	}

	public <T> Collection<ValidationError> validate(final String name, final T value, final Collection<ValidationConstraint<T>> constraints) {
		final List<ValidationError> result = new ArrayList<ValidationError>();
		for (final ValidationConstraint<T> constraint : constraints) {
			if (!constraint.validate(value)) {
				result.add(new ValidationErrorSimple(name + " is invalid"));
			}
		}
		return result;
	}
}
