package de.benjaminborbe.task.core.guice;

import com.google.inject.Inject;
import de.benjaminborbe.task.core.dao.attachment.TaskAttachmentValidator;
import de.benjaminborbe.task.core.dao.context.TaskContextValidator;
import de.benjaminborbe.task.core.dao.task.TaskValidator;
import de.benjaminborbe.tools.validation.ValidatorRegistry;

public class TaskValidatorLinker {

	@Inject
	public static void link(final ValidatorRegistry validatorRegistry, final TaskValidator taskValidator, final TaskContextValidator taskContextValidator, final TaskAttachmentValidator taskAttachmentValidator) {
		validatorRegistry.register(taskValidator);
		validatorRegistry.register(taskContextValidator);
		validatorRegistry.register(taskAttachmentValidator);
	}
}