package com.tugalsan.api.tomcat.embedded.server;

import java.util.*;
import java.nio.file.*;
import java.time.*;
import org.apache.catalina.*;
import org.apache.catalina.startup.*;
import com.tugalsan.api.runnable.client.*;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.thread.server.TS_ThreadKillTrigger;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.api.unsafe.client.*;
import com.tugalsan.api.tomcat.embedded.server.servlets.*;
import com.tugalsan.api.tomcat.embedded.server.utils.*;

public record TS_TomcatBall(
        TS_ThreadKillTrigger killTrigger,
        Path project,
        Path project_src_main_webapp,
        Path project_target_classes,
        Tomcat tomcat,
        Context context,
        CharSequence contextName_as_empty_or_slashName,
        WebResourceRoot resources,
        List<TS_ServletAbstract> servlets,
        List<TS_TomcatConnector> connectors) {

    final private static TS_Log d = TS_Log.of(TS_TomcatBall.class);

    public static TS_TomcatBall of(TS_ThreadKillTrigger killTrigger, CharSequence contextName_as_empty_or_slashName, TGS_RunnableType1<List<TS_ServletAbstract>> servlets, TGS_RunnableType1<List<TS_TomcatConnector>> connectors) {
        var tomcatBall = TS_TomcatBuild.init(killTrigger, contextName_as_empty_or_slashName);
        List<TS_ServletAbstract> servletList = new ArrayList();
        List<TS_TomcatConnector> connectorList = new ArrayList();
        servlets.run(servletList);
        connectors.run(connectorList);
        TS_TomcatBuild.map(tomcatBall, servletList);
        TS_TomcatBuild.map(tomcatBall, new TS_ServletDestroyByMapping(tomcatBall));
        TS_TomcatBuild.startAndLock(tomcatBall, connectorList);
        return tomcatBall;
    }

    public static TS_TomcatConnector connectorOfUnsecure(int port) {
        return TS_TomcatConnector.ofUnSecure(port);
    }

    public static TS_TomcatConnector connectorOfSecure(int port, String keyAlias, String keystorePass, Path keystorePath) {
        return TS_TomcatConnector.ofSecure(port, keyAlias, keystorePass, keystorePath);
    }

    public void destroy() {
        var maxSecondsForConnectors = 5;
        var maxSecondsForTomcat = 5;
        var sequencial = true;
        if (sequencial) {//SEQUENCIAL WAY
            connectors().forEach(connector -> connector.destroy());
            TS_ThreadWait.seconds(TS_TomcatBall.class.getSimpleName(), killTrigger(), maxSecondsForConnectors);//TEST FOR SEQUENCIAL WAY
            TGS_UnSafe.run(() -> context().destroy(), e -> d.ct("context.destroy", e));
            TS_ThreadWait.seconds(TS_TomcatBall.class.getSimpleName(), killTrigger(), maxSecondsForTomcat);//TEST FOR SEQUENCIAL WAY
        } else {
//            {//DESTROR ALL CONNECTORS
//                List<Callable<Boolean>> destroyConnectors = new ArrayList();
//                connectors.forEach(connector -> destroyConnectors.add(() -> {
//                    connector.destroy();
//                    return true;
//                }));
//                var all = TS_ThreadAsyncAll.of(Duration.ofSeconds(maxSecondsForConnectors), destroyConnectors);
//                if (all.hasError()) {
//                    System.out.println("ERROR ON DESTROY CONNECTORS:");
//                    all.exceptions.forEach(e -> {
//                        e.printStackTrace();
//                    });
//                } else {
//                    System.out.println("CONNECTORS DESTROYED SUCCESSFULLY");
//                }
//            }
//            {//DESTROY TOMCAT
//                Callable<Boolean> destroyTomcat = () -> {
//                    context().destroy();
//                    return true;
//                };
//                var all = TS_ThreadAsyncAll.of(Duration.ofSeconds(maxSecondsForTomcat), destroyTomcat);
//                if (all.hasError()) {
//                    System.out.println("ERROR ON DESTROY TOMCAT:");
//                    all.exceptions.forEach(e -> {
//                        e.printStackTrace();
//                    });
//                } else {
//                    System.out.println("TOMCAT DESTROYED SUCCESSFULLY");
//                }
//            }
        }
        {//FINNAL SEALING
            TGS_UnSafe.run(() -> tomcat.stop(), e -> d.ct("tomcat.stop", e));
            TS_ThreadWait.seconds(TS_TomcatBall.class.getSimpleName(), killTrigger(), maxSecondsForTomcat);//TEST FOR SEQUENCIAL WAY
            TGS_UnSafe.run(() -> tomcat.destroy(), e -> d.ct("tomcat.destroy", e));
        }
    }

}
