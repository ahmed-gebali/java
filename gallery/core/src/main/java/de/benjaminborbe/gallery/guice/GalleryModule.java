package de.benjaminborbe.gallery.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.gallery.api.GalleryService;
import de.benjaminborbe.gallery.dao.GalleryCollectionDao;
import de.benjaminborbe.gallery.dao.GalleryCollectionDaoStorage;
import de.benjaminborbe.gallery.dao.GalleryEntryDao;
import de.benjaminborbe.gallery.dao.GalleryEntryDaoStorage;
import de.benjaminborbe.gallery.dao.GalleryGroupDao;
import de.benjaminborbe.gallery.dao.GalleryGroupDaoStorage;
import de.benjaminborbe.gallery.dao.GalleryImageDao;
import de.benjaminborbe.gallery.dao.GalleryImageDaoStorage;
import de.benjaminborbe.gallery.service.GalleryServiceImpl;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;
import org.slf4j.Logger;

import javax.inject.Singleton;

public class GalleryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GalleryService.class).to(GalleryServiceImpl.class).in(Singleton.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);
		bind(GalleryEntryDao.class).to(GalleryEntryDaoStorage.class).in(Singleton.class);
		bind(GalleryImageDao.class).to(GalleryImageDaoStorage.class).in(Singleton.class);
		bind(GalleryCollectionDao.class).to(GalleryCollectionDaoStorage.class).in(Singleton.class);
		bind(GalleryGroupDao.class).to(GalleryGroupDaoStorage.class).in(Singleton.class);

		requestStaticInjection(GalleryValidatorLinker.class);
	}
}
