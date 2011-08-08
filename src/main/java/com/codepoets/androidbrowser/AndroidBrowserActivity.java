package com.codepoets.androidbrowser;


import android.app.Activity;
import android.os.Bundle;
import com.codepoets.websimple.http.HttpServer;

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
            serverThread = new WebServerThread(iface.getInetAddresses().nextElement(), port);
            serverThread.setDaemon(false);
            serverThread.start();
        } catch (SocketException e) {
            logger.error("Socket error:", e);
        }
	}

    class WebServerThread extends Thread {
        private InetAddress inetAddress;
        private int port;

        public WebServerThread(InetAddress inetAddress, int port) {

            this.inetAddress = inetAddress;
            this.port = port;
        }

        @Override
        public void run() {
            HttpServer httpServer = new HttpServer(Arrays.asList(new InetAddress[] { inetAddress }), port, "AndroidBrowserServer/1.0");
            httpServer.init();
            try {
                httpServer.run();
            } catch (IOException e) {
                logger.error("Http Server error:", e);
            }
        }
    }
}
