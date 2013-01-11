package de.benjaminborbe.projectile.gui.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;

import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.projectile.api.ProjectileTeamIdentifier;
import de.benjaminborbe.projectile.gui.ProjectileGuiConstants;
import de.benjaminborbe.tools.url.MapParameter;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.link.LinkRelativWidget;

public class ProjectileLinkFactory {

	private final UrlUtil urlUtil;

	@Inject
	public ProjectileLinkFactory(final UrlUtil urlUtil) {
		this.urlUtil = urlUtil;
	}

	public Widget updateTeam(final HttpServletRequest request, final ProjectileTeamIdentifier id) throws MalformedURLException, UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_TEAM_UPDATE, new MapParameter().add(
				ProjectileGuiConstants.PARAMETER_TEAM_ID, String.valueOf(id)), "update");
	}

	public Widget deleteTeam(final HttpServletRequest request, final ProjectileTeamIdentifier id) throws MalformedURLException, UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_TEAM_DELETE, new MapParameter().add(
				ProjectileGuiConstants.PARAMETER_TEAM_ID, String.valueOf(id)), "delete");
	}

	public Widget createTeam(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_TEAM_CREATE, "Create Team");
	}

	public Widget teamList(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_TEAM_LIST, "List Teams");
	}

	public Widget reportForCurrentUser(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_REPORT_USER_CURRENT, "My Report");
	}

	public Widget reportForCurrentTeam(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_REPORT_TEAM_CURRENT, "My Team Report");
	}

	public Widget reportForAllUser(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_REPORT_USER_ALL, "All Users Report");
	}

	public Widget reportForAllTeam(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_REPORT_TEAM_ALL, "All Teams Report");
	}

	public Widget fetchReport(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_REPORT_FETCH, "Fetch Reports");
	}

	public Widget importReport(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + ProjectileGuiConstants.NAME + ProjectileGuiConstants.URL_REPORT_IMPORT, "Import Reports");
	}

}
