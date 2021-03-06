package de.benjaminborbe.microblog.mock;

import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.microblog.api.MicroblogConversationIdentifier;
import de.benjaminborbe.microblog.api.MicroblogPost;
import de.benjaminborbe.microblog.api.MicroblogPostIdentifier;
import de.benjaminborbe.microblog.api.MicroblogService;
import de.benjaminborbe.microblog.api.MicroblogServiceException;

import java.util.Collection;

public class MicroblogServiceMock implements MicroblogService {

	@Override
	public MicroblogPostIdentifier getLatestPostIdentifier() throws MicroblogServiceException {
		return null;
	}

	@Override
	public void mailPost(final MicroblogPostIdentifier microblogPostIdentifier) throws MicroblogServiceException {
	}

	@Override
	public void mailConversation(final MicroblogConversationIdentifier microblogConversationIdentifier) throws MicroblogServiceException {
	}

	@Override
	public MicroblogConversationIdentifier createMicroblogConversationIdentifier(final long conversationNumber) {
		return null;
	}

	@Override
	public MicroblogPostIdentifier createMicroblogPostIdentifier(final long postNumber) {
		return null;
	}

	@Override
	public MicroblogConversationIdentifier getMicroblogConversationIdentifierForPost(final MicroblogPostIdentifier microblogPostIdentifier) throws MicroblogServiceException {
		return null;
	}

	@Override
	public void refreshPost(final SessionIdentifier sessionIdentifier) throws MicroblogServiceException, PermissionDeniedException, LoginRequiredException {
	}

	@Override
	public void updatePost(final SessionIdentifier sessionIdentifier, final MicroblogPostIdentifier microblogPostIdentifier) throws MicroblogServiceException,
		PermissionDeniedException, LoginRequiredException {
	}

	@Override
	public Collection<String> listNotifications(final UserIdentifier userIdentifier) throws MicroblogServiceException {
		return null;
	}

	@Override
	public void activateNotification(final UserIdentifier userIdentifier, final String keyword) throws MicroblogServiceException {
	}

	@Override
	public void deactivateNotification(final UserIdentifier userIdentifier, final String keyword) throws MicroblogServiceException {
	}

	@Override
	public MicroblogPost getPost(final MicroblogPostIdentifier microblogPostIdentifier) throws MicroblogServiceException {
		return null;
	}

}
