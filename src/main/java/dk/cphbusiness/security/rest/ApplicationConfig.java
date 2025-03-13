package dk.cphbusiness.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.config.JavalinConfig;

import static io.javalin.apibuilder.ApiBuilder.path;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class ApplicationConfig {
    private static ApplicationConfig applicationConfig;
    private static Javalin app;
    private static JavalinConfig javalinConfig;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ApplicationConfig(){}
    private ISecurityController securityController = new SecurityController();

    public static ApplicationConfig getInstance(){
        if(applicationConfig == null){
            applicationConfig = new ApplicationConfig();
        }
        return applicationConfig;
    }

    public ApplicationConfig initiateServer(){
        app = Javalin.create(config -> {
            javalinConfig = config;
            config.http.defaultContentType = "application/json";
            config.router.contextPath = "/api";
            config.bundledPlugins.enableRouteOverview("/routes");
            config.bundledPlugins.enableDevLogging();
        });
        return applicationConfig;
    }
    public ApplicationConfig setRoute(EndpointGroup routes){
        javalinConfig.router.apiBuilder(()->{
            path("/", routes);
        });
        return applicationConfig;
    }
    public ApplicationConfig startServer(int port){
        app.start(port);
        return applicationConfig;
    }

    public ApplicationConfig checkSecurityRoles() {
        app.beforeMatched(securityController.authenticate()); // check if there is a valid token in the header
        app.beforeMatched(securityController.authorize()); // check if the user has the required role
        return applicationConfig;
    }

    public ApplicationConfig handleException(){
        app.exception(Exception.class, (e,ctx)->{
            e.printStackTrace();
            ObjectNode node = objectMapper.createObjectNode();
            node.put("msg",e.getMessage());
            ctx.status(500);
            ctx.json(node);
        });
        return applicationConfig;
    }
    public static void stopServer(){
        app.stop();
        app = null;
    }
}
