package com.codepoets.androidbrowser;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.codepoets.websimple.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;

public class AndroidBrowserActivity extends Activity {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AndroidBrowserActivity.class);

    private WebServerThread serverThread;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            NetworkInterface iface = NetworkInterface.getByName("lo");
            int port = 6000;
            serverThread = new WebServerThread(this, logger, iface.getInetAddresses().nextElement(), port);
            serverThread.setDaemon(false);
            serverThread.start();
        } catch (SocketException e) {
            logger.error("Socket error:", e);
        }
	}

    static class WebServerThread extends Thread {
        private Context context;
        private Logger logger;
        private InetAddress inetAddress;
        private int port;

        public WebServerThread(Context context, Logger logger, InetAddress inetAddress, int port) {
            this.context = context;
            this.logger = logger;
            this.inetAddress = inetAddress;
            this.port = port;
        }

        @Override
        public void run() {
            HttpServer httpServer = new HttpServer(Arrays.asList(new InetAddress[] { inetAddress }), port, "AndroidBrowserServer/1.0");
            try {
                httpServer.init(context);
                httpServer.run();
            } catch (IOException e) {
                logger.error("Http Server error:", e);
            }
        }
    }
}
