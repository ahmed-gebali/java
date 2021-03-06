package de.benjaminborbe.configuration.mock;

import de.benjaminborbe.api.ValidationException;
import de.benjaminborbe.configuration.api.ConfigurationDescription;
import de.benjaminborbe.configuration.api.ConfigurationIdentifier;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.configuration.api.ConfigurationServiceException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ConfigurationServiceMock implements ConfigurationService {

	private final Map<ConfigurationIdentifier, String> data = new HashMap<ConfigurationIdentifier, String>();

	@Inject
	public ConfigurationServiceMock() {

	}

	@Override
	public String getConfigurationValue(final ConfigurationDescription configuration) throws ConfigurationServiceException {
		if (data.containsKey(configuration.getId())) {
			return getConfigurationValue(configuration.getId());
		}
		return configuration.getDefaultValueAsString();
	}

	@Override
	public String getConfigurationValue(final ConfigurationIdentifier configurationIdentifier) throws ConfigurationServiceException {
		return data.get(configurationIdentifier);
	}

	@Override
	public void setConfigurationValue(final ConfigurationIdentifier configurationIdentifier, final String value) throws ConfigurationServiceException {
		data.put(configurationIdentifier, value);
	}

	public void setConfigurationValue(final ConfigurationIdentifier configurationIdentifier, final Object value) throws ConfigurationServiceException {
		setConfigurationValue(configurationIdentifier, value != null ? String.valueOf(value) : null);
	}

	@Override
	public ConfigurationIdentifier createConfigurationIdentifier(final String id) throws ConfigurationServiceException {
		return new ConfigurationIdentifier(id);
	}

	@Override
	public Collection<ConfigurationDescription> listConfigurations() {
		throw new RuntimeException("not implemeted");
	}

	@Override
	public ConfigurationDescription getConfiguration(final ConfigurationIdentifier configurationIdentifier) throws ConfigurationServiceException {
		throw new RuntimeException("not implemeted");
	}

	@Override
	public void setConfigurationValue(
		final ConfigurationDescription configurationDescription,
		final String value
	) throws ConfigurationServiceException, ValidationException {
		setConfigurationValue(configurationDescription.getId(), value);
	}

}
