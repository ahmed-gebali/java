package de.benjaminborbe.poker.gamecreator;

import com.google.inject.Injector;
import de.benjaminborbe.lib.test.mock.EasyMockHelper;
import de.benjaminborbe.lib.validation.ValidationExecutor;
import de.benjaminborbe.poker.api.PokerGameIdentifier;
import de.benjaminborbe.poker.config.PokerCoreConfig;
import de.benjaminborbe.poker.game.PokerGameBean;
import de.benjaminborbe.poker.game.PokerGameDao;
import de.benjaminborbe.poker.game.PokerGameIdentifierGenerator;
import de.benjaminborbe.poker.guice.PokerModulesMock;
import de.benjaminborbe.tools.guice.GuiceInjectorBuilder;
import org.easymock.EasyMock;
import org.junit.Test;
import org.slf4j.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PokerGameCreatorUnitTest {

	private final EasyMockHelper easyMockHelper = new EasyMockHelper();

	@Test
	public void test_createDefaultGame__validateWithoutError() throws Exception {
		final Injector injector = GuiceInjectorBuilder.getInjector(new PokerModulesMock());
		final Logger logger = easyMockHelper.createNiceMock(Logger.class);
		final PokerGameIdentifier pokerGameIdentifier = new PokerGameIdentifier("123");
		final PokerGameIdentifierGenerator pokerGameIdentifierGenerator = easyMockHelper.createMock(PokerGameIdentifierGenerator.class);
		EasyMock.expect(pokerGameIdentifierGenerator.nextId()).andReturn(pokerGameIdentifier);
		final PokerGameDao pokerGameDao = easyMockHelper.createNiceMock(PokerGameDao.class);
		final PokerGameBean pokerGameBean = new PokerGameBean();
		EasyMock.expect(pokerGameDao.create()).andReturn(pokerGameBean);
		final PokerCoreConfig pokerCoreConfig = easyMockHelper.createNiceMock(PokerCoreConfig.class);
		final ValidationExecutor validationExecutor = injector.getInstance(ValidationExecutor.class);

		easyMockHelper.replay();

		final PokerGameCreator pokerGameCreator = new PokerGameCreator(logger, pokerGameDao, pokerGameIdentifierGenerator, pokerCoreConfig, validationExecutor);

		assertThat(pokerGameCreator.createDefaultGame(), is(pokerGameIdentifier));

		easyMockHelper.verify();
	}
}
