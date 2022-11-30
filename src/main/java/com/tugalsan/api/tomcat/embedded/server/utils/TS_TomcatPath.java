package com.tugalsan.api.tomcat.embedded.server.utils;

import java.nio.file.*;

public class TS_TomcatPath {

    public static Path project_target_classes() {
        return project().resolve("target").resolve("classes");
    }

    public static Path project_src_main_webapp() {
        return project().resolve("src").resolve("main").resolve("webapp");
    }

    public static Path project() {
        return Path.of("").toAbsolutePath();
    }
}
