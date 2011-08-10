package com.codepoets.websimple.http.handler;

import com.codepoets.websimple.http.handler.DefaultDirectoryHandler;
import org.apache.http.protocol.HttpRequestHandler;

public class DefaultHttpRequestHandlerFactory {

    private boolean dirListingEnabled;

    public DefaultHttpRequestHandlerFactory(boolean dirListingEnabled) {
        this.dirListingEnabled = dirListingEnabled;
    }

    public HttpRequestHandler getDirectoryHandler() {
        return new DefaultDirectoryHandler(dirListingEnabled);
    }
}
