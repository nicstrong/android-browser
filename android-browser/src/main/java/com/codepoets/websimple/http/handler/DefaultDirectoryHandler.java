package com.codepoets.websimple.http.handler;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;


public class DefaultDirectoryHandler implements HttpRequestHandler {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultDirectoryHandler.class);

    public DefaultDirectoryHandler(boolean dirListingEnabled) {
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        logger.debug("handle() uri = {}", httpRequest.getRequestLine().getUri());
        logger.debug("handle() context = {}",httpContext);
    }
}
