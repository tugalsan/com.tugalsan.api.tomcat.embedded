package com.tugalsan.api.tomcat.embedded.server;

import jakarta.servlet.http.*;

public abstract class TS_ServletAbstract extends HttpServlet{
    abstract public String name();
}
