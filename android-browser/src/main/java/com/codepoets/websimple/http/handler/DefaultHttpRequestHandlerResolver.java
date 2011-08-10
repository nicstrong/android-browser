package com.codepoets.websimple.http.handler;

import android.text.Html;
import com.codepoets.websimple.filesystem.FileSystem;
import com.codepoets.websimple.filesystem.FileSystemFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerResolver;

import java.io.File;

public class DefaultHttpRequestHandlerResolver implements HttpRequestHandlerResolver {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultHttpRequestHandlerResolver.class);

    private DefaultHttpRequestHandlerFactory handlerFactory;
    private FileSystem webRoot;
	private HttpRequestHandler fileHandler;
	private HttpRequestHandler directoryHandler;

    public DefaultHttpRequestHandlerResolver(FileSystem webRoot) {
        this(webRoot, new DefaultHttpRequestHandlerFactory(webRoot, true));
    }

    public DefaultHttpRequestHandlerResolver(FileSystem webRoot, DefaultHttpRequestHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
        this.webRoot = webRoot;
    }

    @Override
    public HttpRequestHandler lookup(String requestURI) {
        logger.debug("Mapping {} to request handler", requestURI);

	    if (webRoot.exists(requestURI)) {
		    FileSystemFile file = webRoot.getFile(requestURI);
		    if (file.isDirectory()) {
			    return handlerFactory.getDirectoryHandler();
		    } else {
			    return handlerFactory.getFileHandler();
		    }
	    } else {
		    return handlerFactory.getErrorHandler(HttpStatus.SC_NOT_FOUND);
	    }
    }
}
