package dk.cphbusiness.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
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
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static EndpointGroup getRoutes() {
        return () ->
        {
            path("auth", () -> {
                post("register", securityController.register());
                post("login", securityController.login());
            });
            path("secured",()->{
                get("demo", (ctx)->ctx.json(objectMapper.createObjectNode().put("msg","Success")), Role.USER);
            });
        };
    }
    public enum Role implements RouteRole { ANYONE, USER, ADMIN }
}
