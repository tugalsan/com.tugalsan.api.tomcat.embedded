package com.tugalsan.api.tomcat.embedded.server.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.tugalsan.api.tomcat.embedded.server.*;

public class TS_ServletAliveByMapping extends TS_ServletAbstract {

    @Override
    public String name() {
        return TS_ServletAliveByMapping.class.getSimpleName();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(TS_ServletAliveByMapping.class.getName());
        resp.flushBuffer();
    }
}
