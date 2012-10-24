package de.benjaminborbe.scala.service;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.scala.api.ScalaService;

@Singleton
public class ScalaServiceImpl implements ScalaService {

	private final Logger logger;

	@Inject
	public ScalaServiceImpl(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void execute() {
		logger.trace("execute");
	}

}
