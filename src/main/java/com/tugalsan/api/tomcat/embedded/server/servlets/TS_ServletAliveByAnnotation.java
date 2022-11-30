package com.tugalsan.api.tomcat.embedded.server.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebServlet(
        name = "TS_ServletAliveByAnnotation",//JUST A UNQUE TAG
        urlPatterns = {"/TS_ServletAliveByAnnotation"}
)
public class TS_ServletAliveByAnnotation extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(TS_ServletAliveByAnnotation.class.getName());
        resp.flushBuffer();
    }
}
