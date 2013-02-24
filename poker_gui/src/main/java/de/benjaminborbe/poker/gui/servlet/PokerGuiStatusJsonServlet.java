package de.benjaminborbe.poker.gui.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.AuthenticationService;
import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.poker.api.PokerGame;
import de.benjaminborbe.poker.api.PokerPlayer;
import de.benjaminborbe.poker.api.PokerPlayerIdentifier;
import de.benjaminborbe.poker.api.PokerService;
import de.benjaminborbe.poker.api.PokerServiceException;
import de.benjaminborbe.poker.gui.PokerGuiConstants;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.json.JSONArray;
import de.benjaminborbe.tools.json.JSONArraySimple;
import de.benjaminborbe.tools.json.JSONObject;
import de.benjaminborbe.tools.json.JSONObjectSimple;
import de.benjaminborbe.tools.url.UrlUtil;

@Singleton
public class PokerGuiStatusJsonServlet extends PokerGuiJsonServlet {

	private static final long serialVersionUID = 1328676176772634649L;

	private final PokerService pokerService;

	@Inject
	public PokerGuiStatusJsonServlet(
			final Logger logger,
			final UrlUtil urlUtil,
			final AuthenticationService authenticationService,
			final AuthorizationService authorizationService,
			final CalendarUtil calendarUtil,
			final TimeZoneUtil timeZoneUtil,
			final Provider<HttpContext> httpContextProvider,
			final PokerService pokerService) {
		super(logger, urlUtil, authenticationService, authorizationService, calendarUtil, timeZoneUtil, httpContextProvider, pokerService);
		this.pokerService = pokerService;
	}

	@Override
	protected void doService(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws ServletException, IOException,
			PermissionDeniedException, LoginRequiredException {
		try {
			final JSONObject jsonObject = new JSONObjectSimple();
			final PokerPlayerIdentifier playerIdentifier = pokerService.createPlayerIdentifier(request.getParameter(PokerGuiConstants.PARAMETER_PLAYER_ID));
			final PokerPlayer player = pokerService.getPlayer(playerIdentifier);
			final PokerGame game = pokerService.getGame(player.getGame());
			jsonObject.put("bid", game.getBet());
			jsonObject.put("bigBlind", game.getBigBlind());
			jsonObject.put("id", game.getId());
			jsonObject.put("gameName", game.getName());
			jsonObject.put("pot", game.getPot());
			jsonObject.put("round", game.getRound());
			jsonObject.put("running", game.getRunning());
			jsonObject.put("smallBlind", game.getSmallBlind());
			jsonObject.put("activePlayer", pokerService.getActivePlayer(player.getGame()));
			final JSONArray jsonPlayers = new JSONArraySimple();
			for (final PokerPlayerIdentifier pid : game.getPlayers()) {
				jsonPlayers.add(pid);
			}
			jsonObject.put("playerCredits", player.getAmount());
			jsonObject.put("playerName", player.getName());

			printJson(response, jsonObject);
		}
		catch (final PokerServiceException e) {
			printException(response, e);
		}
	}
}
