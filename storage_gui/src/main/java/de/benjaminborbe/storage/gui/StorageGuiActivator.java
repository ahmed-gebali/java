package de.benjaminborbe.storage.gui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

import de.benjaminborbe.storage.gui.guice.StorageGuiModules;
import de.benjaminborbe.storage.gui.servlet.StorageBackupServlet;
import de.benjaminborbe.storage.gui.servlet.StorageDeleteServlet;
import de.benjaminborbe.storage.gui.servlet.StorageGuiServlet;
import de.benjaminborbe.storage.gui.servlet.StorageListServlet;
import de.benjaminborbe.storage.gui.servlet.StorageReadServlet;
import de.benjaminborbe.storage.gui.servlet.StorageRestoreServlet;
import de.benjaminborbe.storage.gui.servlet.StorageWriteServlet;
import de.benjaminborbe.tools.guice.Modules;
import de.benjaminborbe.tools.osgi.HttpBundleActivator;
import de.benjaminborbe.tools.osgi.ServletInfo;

public class StorageGuiActivator extends HttpBundleActivator {

	@Inject
	private StorageBackupServlet storageBackupServlet;

	@Inject
	private StorageGuiServlet storageGuiServlet;

	@Inject
	private StorageReadServlet storageReadServlet;

	@Inject
	private StorageDeleteServlet storageDeleteServlet;

	@Inject
	private StorageListServlet storageListServlet;

	@Inject
	private StorageWriteServlet storageWriteServlet;

	@Inject
	private StorageRestoreServlet storageRestoreServlet;

	public StorageGuiActivator() {
		super(StorageGuiConstants.NAME);
	}

	@Override
	protected Modules getModules(final BundleContext context) {
		return new StorageGuiModules(context);
	}

	@Override
	protected Collection<ServletInfo> getServletInfos() {
		final Set<ServletInfo> result = new HashSet<ServletInfo>(super.getServletInfos());
		result.add(new ServletInfo(storageGuiServlet, StorageGuiConstants.URL_HOME));
		result.add(new ServletInfo(storageReadServlet, StorageGuiConstants.URL_READ));
		result.add(new ServletInfo(storageWriteServlet, StorageGuiConstants.URL_WRITE));
		result.add(new ServletInfo(storageDeleteServlet, StorageGuiConstants.URL_DELETE));
		result.add(new ServletInfo(storageListServlet, StorageGuiConstants.URL_LIST));
		result.add(new ServletInfo(storageBackupServlet, StorageGuiConstants.URL_BACKUP));
		result.add(new ServletInfo(storageRestoreServlet, StorageGuiConstants.URL_RESTORE));
		return result;
	}

}
