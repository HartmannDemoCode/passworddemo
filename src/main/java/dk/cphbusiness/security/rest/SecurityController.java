package dk.cphbusiness.security.rest;

import dk.bugelhartmann.UserDTO;
import dk.cphbusiness.security.UserDAO;
import dk.cphbusiness.security.entities.User;
import io.javalin.http.Handler;

import java.util.Set;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class SecurityController implements ISecurityController {
    private UserDAO userDAO = UserDAO.getInstance();
    @Override
    public Handler register(){
        return (ctx)->{
            UserDTO newUser = ctx.bodyAsClass(UserDTO.class);
            userDAO.createUser(new User(newUser.getUsername(), newUser.getPassword()));
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
