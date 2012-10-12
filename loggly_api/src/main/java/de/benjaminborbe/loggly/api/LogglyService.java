package de.benjaminborbe.loggly.api;

public interface LogglyService {

	void trace(String message);

	void debug(String message);

	void info(String message);

	void warn(String message);

	void error(String message);

	void fatal(String message);
}
