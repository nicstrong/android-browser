package com.codepoets.websimple.http.handler;

import com.codepoets.websimple.filesystem.FileSystem;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerResolver;

import java.io.File;

public class DefaultHttpRequestHandlerResolver implements HttpRequestHandlerResolver {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultHttpRequestHandlerResolver.class);

    private DefaultHttpRequestHandlerFactory handlerFactory;
    private boolean dirListingEnabled;
    private FileSystem webRoot;
    private HttpRequestHandler directoryHandler;

    public DefaultHttpRequestHandlerResolver(FileSystem webRoot) {
        this(webRoot, new DefaultHttpRequestHandlerFactory(true), true);
    }

    public DefaultHttpRequestHandlerResolver(FileSystem webRoot, DefaultHttpRequestHandlerFactory handlerFactory, boolean dirListingEnabled) {
        this.handlerFactory = handlerFactory;
        this.dirListingEnabled = true;
        this.webRoot = webRoot;
    }

    @Override
    public HttpRequestHandler lookup(String requestURI) {
        logger.debug("Mapping {} to request handler", requestURI);
        File normalisedPath = normalisePath(requestURI);
        //if (normalisedPath.isDirectory()) {
            return getDirectoryHandler();
        //}
        //return null;
    }

    public boolean isDirListingEnabled() {
        return dirListingEnabled;
    }

    public void setDirListingEnabled(boolean dirListingEnabled) {
        this.dirListingEnabled = dirListingEnabled;
    }

    private File normalisePath(String requestURI) {
        //FilenameUtils.normalize();
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private HttpRequestHandler getDirectoryHandler() {
        if (directoryHandler == null) {
            directoryHandler = handlerFactory.getDirectoryHandler();
        }
        return directoryHandler;
    }

}
