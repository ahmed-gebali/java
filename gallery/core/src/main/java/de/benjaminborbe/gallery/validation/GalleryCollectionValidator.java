package de.benjaminborbe.gallery.validation;

import de.benjaminborbe.api.ValidationError;
import de.benjaminborbe.gallery.dao.GalleryCollectionBean;
import de.benjaminborbe.lib.validation.ValidationConstraintValidator;
import de.benjaminborbe.lib.validation.ValidatorBase;
import de.benjaminborbe.lib.validation.ValidatorRule;
import de.benjaminborbe.lib.validation.constraint.ValidationConstraint;
import de.benjaminborbe.lib.validation.constraint.ValidationConstraintNotNull;
import de.benjaminborbe.lib.validation.constraint.ValidationConstraintStringMaxLength;
import de.benjaminborbe.lib.validation.constraint.ValidationConstraintStringMinLength;
import de.benjaminborbe.lib.validation.constraint.ValidationConstraintStringOnlyLetters;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryCollectionValidator extends ValidatorBase<GalleryCollectionBean> {

	private final ValidationConstraintValidator validationConstraintValidator;

	@Inject
	public GalleryCollectionValidator(final ValidationConstraintValidator validationConstraintValidator) {
		this.validationConstraintValidator = validationConstraintValidator;
	}

	@Override
	public Class<GalleryCollectionBean> getType() {
		return GalleryCollectionBean.class;
	}

	@Override
	protected Map<String, ValidatorRule<GalleryCollectionBean>> buildRules() {
		final Map<String, ValidatorRule<GalleryCollectionBean>> result = new HashMap<String, ValidatorRule<GalleryCollectionBean>>();

		// name
		{
			final String field = "name";
			result.put(field, new ValidatorRule<GalleryCollectionBean>() {

				@Override
				public Collection<ValidationError> validate(final GalleryCollectionBean bean) {
					final String value = bean.getName();
					final List<ValidationConstraint<String>> constraints = new ArrayList<ValidationConstraint<String>>();
					constraints.add(new ValidationConstraintNotNull<String>());
					constraints.add(new ValidationConstraintStringMinLength(1));
					constraints.add(new ValidationConstraintStringMaxLength(255));
					constraints.add(new ValidationConstraintStringOnlyLetters());
					return validationConstraintValidator.validate(field, value, constraints);
				}
			});
		}

		return result;
	}

}
