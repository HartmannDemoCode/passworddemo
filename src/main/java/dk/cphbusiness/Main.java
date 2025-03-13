package dk.cphbusiness;

import dk.cphbusiness.security.rest.ApplicationConfig;
import dk.cphbusiness.security.rest.Routes;
import dk.cphbusiness.utils.Utils;
import io.javalin.json.JavalinJackson;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig
                .getInstance()
                .initiateServer()
                .setRoute(Routes.getRoutes())
                .startServer(7070);
    }
}