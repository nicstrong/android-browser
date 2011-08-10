package com.codepoets.websimple.http.handler;

import com.codepoets.websimple.filesystem.FileSystem;
import org.apache.http.protocol.HttpRequestHandler;

public class DefaultHttpRequestHandlerFactory {

	private FileSystem fileSystem;
	private boolean dirListingEnabled;

    public DefaultHttpRequestHandlerFactory(FileSystem fileSystem, boolean dirListingEnabled) {
	    this.fileSystem = fileSystem;
	    this.dirListingEnabled = dirListingEnabled;
    }

    public HttpRequestHandler getDirectoryHandler() {
        return new DefaultDirectoryHandler(fileSystem, dirListingEnabled);
    }

	public HttpRequestHandler getFileHandler() {
        return new DefaultFileHandler(fileSystem);
    }

	public HttpRequestHandler getErrorHandler(int statusCode) {
        return new DefaultErrorHandler(statusCode);
    }
}
