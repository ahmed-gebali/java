package de.benjaminborbe.poker.guice;

import org.slf4j.Logger;

import com.google.inject.AbstractModule;
import javax.inject.Singleton;

import de.benjaminborbe.poker.api.PokerService;
import de.benjaminborbe.poker.config.PokerConfig;
import de.benjaminborbe.poker.config.PokerConfigImpl;
import de.benjaminborbe.poker.game.PokerGameDao;
import de.benjaminborbe.poker.game.PokerGameDaoStorage;
import de.benjaminborbe.poker.player.PokerPlayerDao;
import de.benjaminborbe.poker.player.PokerPlayerDaoStorage;
import de.benjaminborbe.poker.service.PokerServiceImpl;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;

public class PokerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PokerConfig.class).to(PokerConfigImpl.class).in(Singleton.class);
		bind(PokerPlayerDao.class).to(PokerPlayerDaoStorage.class).in(Singleton.class);
		bind(PokerGameDao.class).to(PokerGameDaoStorage.class).in(Singleton.class);
		bind(PokerService.class).to(PokerServiceImpl.class).in(Singleton.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);

		requestStaticInjection(PokerValidatorLinker.class);
	}
}