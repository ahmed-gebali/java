package de.benjaminborbe.mail.util;

import de.benjaminborbe.tools.jndi.InitialContextCache;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.Session;
import javax.naming.Context;
import javax.naming.NamingException;

@Singleton
public class MailSessionFactoryImpl implements MailSessionFactory {

	private final Logger logger;

	private final InitialContextCache initialContextCache;

	@Inject
	public MailSessionFactoryImpl(final Logger logger, final InitialContextCache initialContextCache) {
		this.logger = logger;
		this.initialContextCache = initialContextCache;
	}

	@Override
	public Session getInstance() throws NamingException {
		logger.trace("getInstance()");
		final Context envCtx = (Context) initialContextCache.lookup("java:comp/env");
		final Session session = (Session) envCtx.lookup("mail/Session");
		return session;
	}

}
