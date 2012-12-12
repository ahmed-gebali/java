package de.benjaminborbe.gallery.validation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.benjaminborbe.api.ValidationError;
import de.benjaminborbe.api.ValidationErrorSimple;
import de.benjaminborbe.gallery.dao.GalleryCollectionBean;
import de.benjaminborbe.tools.validation.Validator;

public class GalleryCollectionValidator implements Validator<GalleryCollectionBean> {

	@Override
	public Class<GalleryCollectionBean> getType() {
		return GalleryCollectionBean.class;
	}

	public Collection<ValidationError> validate(final GalleryCollectionBean bean) {
		final Set<ValidationError> result = new HashSet<ValidationError>();

		// validate name
		final String name = bean.getName();
		{
			if (name == null || name.length() == 0) {
				result.add(new ValidationErrorSimple("name missing"));
			}
		}

		return result;
	}

	@Override
	public Collection<ValidationError> validateObject(final Object object) {
		return validate((GalleryCollectionBean) object);
	}

}
