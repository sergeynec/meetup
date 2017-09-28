package ru.necs.functional;

import java.io.File;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("h2")
@Configuration
public class AppServerConfig {

    @Bean
    public WebAppContext webAppContext() {
        final File explodedWarSource = new File("../sample-project/war/target/app");

        final WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/functional-test");
        webAppContext.setParentLoaderPriority(true);
        webAppContext.setWar(explodedWarSource.getAbsolutePath());
        webAppContext.setConfigurations(
                new org.eclipse.jetty.webapp.Configuration[]{
                        new AnnotationConfiguration(),
                        new WebInfConfiguration()
                }
        );

        return webAppContext;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server appServer() {
        final Server server = new Server(8008);
        server.setHandler(webAppContext());

        return server;
    }
}
