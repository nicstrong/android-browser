package com.codepoets.websimple.http;

import java.io.IOException;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.SyncBasicHttpContext;

import android.util.Log;

class RequestWorkerThread extends Thread {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RequestWorkerThread.class);

	private final HttpService httpservice;
	private final HttpServerConnection conn;

	public RequestWorkerThread(final HttpService httpservice, final HttpServerConnection conn) {
		super();
		this.setName(RequestWorkerThread.class.getName());
		this.httpservice = httpservice;
		this.conn = conn;
	}

	public void run() {
		logger.debug("New connection thread");
		HttpContext context = new SyncBasicHttpContext(null);
		try {
			while (!Thread.interrupted() && this.conn.isOpen()) {
				this.httpservice.handleRequest(this.conn, context);
			}
		} catch (ConnectionClosedException ex) {
			logger.error("Client closed connection", ex);
		} catch (IOException ex) {
			logger.error("I/O error", ex);
		} catch (HttpException ex) {
			logger.error("Unrecoverable HTTP protocol violation", ex);
		} finally {
			try {
				this.conn.shutdown();
			} catch (IOException ignore) {
			}
		}
	}

}
