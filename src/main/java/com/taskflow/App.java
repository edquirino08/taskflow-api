package com.taskflow;

import java.util.logging.Logger;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.taskflow.controller.user.UserController;
import com.taskflow.service.UserService;

import jakarta.inject.Singleton;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        // Configuração do Jersey
        ResourceConfig config = new ResourceConfig();
        config.register(JacksonFeature.class);
        config.register(UserController.class);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(UserService.class).in(Singleton.class);
            }
        });

        // Cria o Tomcat embutido
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector(); // força criação do conector HTTP na porta configurada

        // Contexto raiz ("/")
        Context context = tomcat.addContext("", System.getProperty("java.io.tmpdir"));

        // Servlet do Jersey usando a configuração acima
        ServletContainer jerseyServlet = new ServletContainer(config);
        Tomcat.addServlet(context, "jersey-servlet", jerseyServlet);
        context.addServletMappingDecoded("/api/*", "jersey-servlet");

        tomcat.start();
        LOGGER.info("Tomcat embutido iniciado em http://localhost:8080/api/user (POST)");
        tomcat.getServer().await();
    }
}
