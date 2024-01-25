package com.tugalsan.api.tomcat.embedded.server.servlets;

import com.tugalsan.api.tomcat.embedded.server.TS_ServletAbstract;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.tugalsan.api.tomcat.embedded.server.*;

public class TS_ServletDestroyByMapping extends TS_ServletAbstract {

//    final private static TS_Log d = TS_Log.of(TS_ServletDestroyByMapping.class);

    public TS_ServletDestroyByMapping(TS_TomcatBall tomcatBall) {
        this.tomcatBall = tomcatBall;
    }
    final private TS_TomcatBall tomcatBall;

    @Override
    public String name() {
        return TS_ServletDestroyByMapping.class.getSimpleName();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(TS_ServletDestroyByMapping.class.getName());
        resp.flushBuffer();
        tomcatBall.destroy();
    }

}
