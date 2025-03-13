package dk.cphbusiness.security.rest;

import dk.bugelhartmann.UserDTO;
import io.javalin.http.Handler;

import java.util.Set;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class SecurityController implements ISecurityController {

    @Override
    public Handler register(){
        return (ctx)->{
            UserDTO newUser = ctx.bodyAsClass(UserDTO.class);
            ctx.json(newUser);
        };
    }

    @Override
    public Handler authenticate() {
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean authorize(UserDTO userDTO, Set<String> allowedRoles) {
        //return false;

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String createToken(UserDTO user) throws Exception {
        //return "";

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public UserDTO verifyToken(String token) throws Exception {
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Handler login(){
        return (ctx)->{

        };

    }
}
