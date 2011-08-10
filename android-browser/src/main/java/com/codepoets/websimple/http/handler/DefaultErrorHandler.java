package com.codepoets.websimple.http.handler;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.EnglishReasonPhraseCatalog;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.util.Locale;

public class DefaultErrorHandler implements HttpRequestHandler {
    private int statusCode;
	private final String ERROR_HTML = "<html><head><title>%s - Error report</title></head><body><h1>HTTP Status %d - %s</h1></body></html>";

	public DefaultErrorHandler(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
		HttpParams params = httpRequest.getParams();
		String html = String.format(ERROR_HTML, params.getParameter(CoreProtocolPNames.ORIGIN_SERVER),
				statusCode, EnglishReasonPhraseCatalog.INSTANCE.getReason(statusCode, Locale.ENGLISH));
		httpResponse.setHeader("Content-Type", "text/html");
		httpResponse.setEntity(new StringEntity(html, HTTP.UTF_8));
		httpResponse.setStatusCode(statusCode);
	}
}
