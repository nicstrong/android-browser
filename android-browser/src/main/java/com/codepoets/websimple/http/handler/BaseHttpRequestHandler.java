package com.codepoets.websimple.http.handler;

import com.codepoets.websimple.http.HttpVerbs;
import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;

public abstract class BaseHttpRequestHandler implements HttpRequestHandler, MethodHandler {
	private final String[] supportedMethods;

	public BaseHttpRequestHandler(String[] supportedMethods) {
		super();
		this.supportedMethods = supportedMethods;
	}

	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
		String method = request.getRequestLine().getMethod().toUpperCase();

		if (!isSupportedMethod(method))
			throw new MethodNotSupportedException(method + " method not supported");

		String path = request.getRequestLine().getUri();

		if (request instanceof HttpEntityEnclosingRequest) {
			if (method.equals(HttpVerbs.GET) || method.equals(HttpVerbs.HEAD))
				throw new ProtocolException("Entity present on HEAD or GET request");
		}

		if (method.endsWith(HttpVerbs.GET))
			handleGet(path, request, response, context);
		else if (method.endsWith(HttpVerbs.PUT))
			handlePut(path, request, response, context);
		else if (method.endsWith(HttpVerbs.POST))
			handlePost(path, request, response, context);
		else if (method.endsWith(HttpVerbs.HEAD))
			handleHead(path, request, response, context);
	}

	boolean isSupportedMethod(String method) {
		for (String meth : supportedMethods) {
			if (meth.equalsIgnoreCase(method))
				return true;
		}
		return false;
	}

	@Override
	public void handleGet(String path, HttpRequest request, HttpResponse response, HttpContext context) {
		throw new UnsupportedOperationException("Must be overloaded in derived class");
	}

	@Override
	public void handlePut(String path, HttpRequest request, HttpResponse response, HttpContext context) {
		throw new UnsupportedOperationException("Must be overloaded in derived class");
	}

	@Override
	public void handlePost(String path, HttpRequest request, HttpResponse response, HttpContext context) {
		throw new UnsupportedOperationException("Must be overloaded in derived class");
	}

	@Override
	public void handleHead(String path, HttpRequest request, HttpResponse response, HttpContext context) {
		throw new UnsupportedOperationException("Must be overloaded in derived class");
	}
}
