package com.codepoets.websimple.http.handler;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public interface MethodHandler
{
	void handleGet(String path, HttpRequest request, HttpResponse response, HttpContext context);
	void handlePut(String path, HttpRequest request, HttpResponse response, HttpContext context);
	void handlePost(String path, HttpRequest request, HttpResponse response, HttpContext context);
	void handleHead(String path, HttpRequest request, HttpResponse response, HttpContext context);
}
