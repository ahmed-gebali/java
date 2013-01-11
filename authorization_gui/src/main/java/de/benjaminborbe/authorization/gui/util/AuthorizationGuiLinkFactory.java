package de.benjaminborbe.authorization.gui.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;

import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.RoleIdentifier;
import de.benjaminborbe.authorization.gui.AuthorizationGuiConstants;
import de.benjaminborbe.html.api.Widget;
import de.benjaminborbe.tools.url.MapParameter;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.website.link.LinkRelativWidget;

public class AuthorizationGuiLinkFactory {

	private final UrlUtil urlUtil;

	@Inject
	public AuthorizationGuiLinkFactory(final UrlUtil urlUtil) {
		this.urlUtil = urlUtil;
	}

	public Widget roleAddPermission(final HttpServletRequest request, final RoleIdentifier roleIdentifier) throws MalformedURLException, UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + AuthorizationGuiConstants.NAME + AuthorizationGuiConstants.URL_ROLE_ADD_PERMISSION, new MapParameter().add(
				AuthorizationGuiConstants.PARAMETER_ROLE_ID, roleIdentifier.getId()), "add permission");
	}

	public Widget roleAddUser(final HttpServletRequest request, final RoleIdentifier roleIdentifier) throws MalformedURLException, UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + AuthorizationGuiConstants.NAME + AuthorizationGuiConstants.URL_ADD_ROLE, new MapParameter().add(
				AuthorizationGuiConstants.PARAMETER_ROLE_ID, roleIdentifier.getId()), "add user");
	}

	public Widget roleInfo(final HttpServletRequest request, final RoleIdentifier roleIdentifier) throws MalformedURLException, UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + AuthorizationGuiConstants.NAME + AuthorizationGuiConstants.URL_ROLE_INFO, new MapParameter().add(
				AuthorizationGuiConstants.PARAMETER_ROLE_ID, String.valueOf(roleIdentifier)), roleIdentifier.getId());
	}

	public Widget roleCreate(final HttpServletRequest request) throws MalformedURLException {
		return new LinkRelativWidget(request, "/" + AuthorizationGuiConstants.NAME + AuthorizationGuiConstants.URL_ROLE_CREATE, "add role");
	}

	public Widget roleDelete(final HttpServletRequest request, final RoleIdentifier roleIdentifier) throws MalformedURLException, UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + AuthorizationGuiConstants.NAME + AuthorizationGuiConstants.URL_ROLE_DELETE, new MapParameter().add(
				AuthorizationGuiConstants.PARAMETER_ROLE_ID, String.valueOf(roleIdentifier)), "delete");
	}

	public Widget userInfo(final HttpServletRequest request, final UserIdentifier userIdentifier) throws MalformedURLException, UnsupportedEncodingException {
		return new LinkRelativWidget(urlUtil, request, "/" + AuthorizationGuiConstants.NAME + AuthorizationGuiConstants.URL_USER_INFO, new MapParameter().add(
				AuthorizationGuiConstants.PARAMETER_USER_ID, userIdentifier.getId()), userIdentifier.getId());
	}
}
