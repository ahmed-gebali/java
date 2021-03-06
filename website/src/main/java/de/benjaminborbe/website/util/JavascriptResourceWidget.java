package de.benjaminborbe.website.util;

import de.benjaminborbe.html.api.HttpContext;
import de.benjaminborbe.html.api.JavascriptResource;
import de.benjaminborbe.html.api.Widget;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class JavascriptResourceWidget implements Widget {

	private final Collection<JavascriptResource> javascriptResources;

	@Inject
	public JavascriptResourceWidget(final Collection<JavascriptResource> javascriptResources) {
		this.javascriptResources = javascriptResources;
	}

	@Override
	public void render(final HttpServletRequest request, final HttpServletResponse response, final HttpContext context) throws IOException {
		final HtmlListWidget widgets = new HtmlListWidget();
		for (final JavascriptResource javascriptResource : javascriptResources) {
			widgets.add("<script type=\"text/javascript\" src=\"" + javascriptResource.getUrl() + "\"></script>\n");
		}
		widgets.render(request, response, context);
	}

}
