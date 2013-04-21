package de.benjaminborbe.tools.http;

import de.benjaminborbe.tools.util.Encoding;

import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpDownloadResultImpl implements HttpDownloadResult, Serializable {

	private static final long serialVersionUID = -3165056995643380843L;

	private final URL url;

	private final long duration;

	private final byte[] content;

	private final Encoding contentEncoding;

	private final String contentType;

	private final Map<String, List<String>> headers;

	public HttpDownloadResultImpl(final URL url, final long duration, final byte[] content, final String contentType, final Encoding contentEncoding, final Map<String, List<String>> headers) {
		this.url = url;
		this.duration = duration;
		this.contentType = contentType;
		this.content = Arrays.copyOf(content, content.length);
		this.contentEncoding = contentEncoding;
		this.headers = headers;
	}

	@Override
	public URL getUrl() {
		return url;
	}

	public long getDuration() {
		return duration;
	}

	public byte[] getContent() {
		return content;
	}

	public Encoding getContentEncoding() {
		return contentEncoding;
	}

	public String getContentType() {
		return contentType;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

}