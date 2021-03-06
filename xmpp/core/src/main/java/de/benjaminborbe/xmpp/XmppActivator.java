package de.benjaminborbe.xmpp;

import de.benjaminborbe.configuration.api.ConfigurationDescription;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.BaseBundleActivator;
import de.benjaminborbe.tools.osgi.ServiceInfo;
import de.benjaminborbe.xmpp.api.XmppCommand;
import de.benjaminborbe.xmpp.api.XmppService;
import de.benjaminborbe.xmpp.config.XmppConfig;
import de.benjaminborbe.xmpp.connector.XmppCommandRegistry;
import de.benjaminborbe.xmpp.connector.XmppCommandServiceTracker;
import de.benjaminborbe.xmpp.connector.XmppConnector;
import de.benjaminborbe.xmpp.connector.XmppConnectorException;
import de.benjaminborbe.xmpp.guice.XmppModules;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class XmppActivator extends BaseBundleActivator {

	@Inject
	private XmppConnector xmppConnector;

	@Inject
	private XmppService xmppService;

	@Inject
	private XmppConfig xmppConfig;

	@Inject
	private Logger logger;

	@Inject
	private XmppCommandRegistry xmppCommandRegistry;

	@Override
	protected Modules getModules(final BundleContext context) {
		return new XmppModules(context);
	}

	@Override
	public Collection<ServiceInfo> getServiceInfos() {
		final Set<ServiceInfo> result = new HashSet<ServiceInfo>(super.getServiceInfos());
		result.add(new ServiceInfo(XmppService.class, xmppService));
		for (final ConfigurationDescription configuration : xmppConfig.getConfigurations()) {
			result.add(new ServiceInfo(ConfigurationDescription.class, configuration, configuration.getName()));
		}
		return result;
	}

	@Override
	public Collection<ServiceTracker> getServiceTrackers(final BundleContext context) {
		final Set<ServiceTracker> serviceTrackers = new HashSet<ServiceTracker>(super.getServiceTrackers(context));
		serviceTrackers.add(new XmppCommandServiceTracker(xmppCommandRegistry, context, XmppCommand.class));
		return serviceTrackers;
	}

	@Override
	protected void onStopped() throws Exception {
		super.onStopped();

		try {
			xmppConnector.disconnect();
		} catch (final XmppConnectorException e) {
			logger.debug(e.getClass().getName(), e);
		}
	}

}
