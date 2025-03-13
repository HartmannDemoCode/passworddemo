package dk.cphbusiness.security.rest;

import io.javalin.apibuilder.EndpointGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class Routes {

    private static Logger logger = LoggerFactory.getLogger(Routes.class);
    private static ISecurityController securityController = new SecurityController();

    public static EndpointGroup getRoutes() {
        return () ->
        {
            path("auth", () -> {
                post("register", securityController.register());
                post("login", (ctx)->{});
            });
        };
    }
}
