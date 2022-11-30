package com.tugalsan.api.tomcat.embedded.server.listeners;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.time.client.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class TS_TomcatListenerByAnnotation implements ServletContextListener {

    final private static TS_Log d = TS_Log.of(TS_TomcatListenerByAnnotation.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        d.cr("contextInitialized", TGS_Time.toString_now());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        d.cr("contextDestroyed", TGS_Time.toString_now());
    }
}
