package com.codepoets.websimple.http;

import android.content.Context;
import com.codepoets.websimple.filesystem.AssetManagerFileSystem;
import com.codepoets.websimple.filesystem.FileSystem;
import com.codepoets.websimple.http.handler.DefaultHttpRequestHandlerResolver;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.*;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HttpServer {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HttpServer.class);

    private final List<InetAddress> inetAddresses;
    private final int port;
    private final HttpParams params;

    private ServerSocket serverSocket;
    private HttpService httpService;
    private boolean shutdown;

    public HttpServer(List<InetAddress> inetAddresses, int port, String serverName) {
        this(inetAddresses, port, createDefaultParams(serverName));
    }

    public HttpServer(List<InetAddress> inetAddresses, int port, HttpParams params) {
        this.inetAddresses = inetAddresses;
        this.port = port;

        this.params = params;
    }

    public void init(Context context) throws IOException {
        BasicHttpProcessor httpProcessor = new BasicHttpProcessor();
        httpProcessor.addInterceptor(new ResponseDate());
        httpProcessor.addInterceptor(new ResponseServer());
        httpProcessor.addInterceptor(new ResponseContent());
        httpProcessor.addInterceptor(new ResponseConnControl());

        FileSystem fileSystem = new AssetManagerFileSystem(context.getAssets());
        HttpRequestHandlerResolver handlerResolver = new DefaultHttpRequestHandlerResolver(fileSystem);

        httpService = new HttpService(httpProcessor, new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory());
        httpService.setParams(this.params);
        httpService.setHandlerResolver(handlerResolver);
    }

    public void run() throws IOException {
	    if (inetAddresses.size() == 0) {
		    logger.warn("No addresses to bind too");
	    }
        boolean socketSuccess = false;
	    logger.debug("Trying to bind to {} addresses", inetAddresses.size());
        for (InetAddress address : inetAddresses) {
            try {
	            logger.debug("Trying to bind to {}", address);
                if (address instanceof Inet4Address) {
                    serverSocket = new ServerSocket(port, 5, address);
                    socketSuccess = true;
                    break;
                }
            } catch (IOException e) {
                logger.error("Unable to connect to socket {}", address.getHostAddress());
            }
        }
        if (!socketSuccess) {
            throw new IOException("Unable to connect to the selected network socket");
        }

        while (!Thread.interrupted() && !shutdown) {
            try {
                Socket socket = serverSocket.accept();
                if (!shutdown) {
                    logger.info("Incoming connection from {}", socket.getInetAddress());
                    DefaultHttpServerConnection conn = new DefaultHttpServerConnection();
                    conn.bind(socket, this.params);

                    Thread t = new RequestWorkerThread(this.httpService, conn);
                    t.setDaemon(true);
                    t.start();
                }
            } catch (InterruptedIOException ex) {
                break;
            } catch (IOException e) {
                if (!shutdown)
                    logger.error("I/O error initialising connection thread:", e);
                break;
            }

        }
    }

    private static HttpParams createDefaultParams(String serverName) {
        BasicHttpParams params = new BasicHttpParams();
        params
                .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)
                .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
                .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false)
                .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)
                .setParameter(CoreProtocolPNames.ORIGIN_SERVER, serverName);
        return params;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    public void shutdown() throws IOException {
        setShutdown(true);
        serverSocket.close();
    }
}
