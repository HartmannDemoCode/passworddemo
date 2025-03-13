package dk.cphbusiness.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dk.bugelhartmann.ITokenSecurity;
import dk.bugelhartmann.TokenSecurity;
import dk.bugelhartmann.UserDTO;
import dk.cphbusiness.exceptions.ApiException;
import dk.cphbusiness.exceptions.ValidationException;
import dk.cphbusiness.security.UserDAO;
import dk.cphbusiness.security.entities.User;
import dk.cphbusiness.utils.Utils;
import io.javalin.http.Handler;
import jakarta.persistence.EntityNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class SecurityController implements ISecurityController {

    ITokenSecurity tokenSecurity = new TokenSecurity();
    ObjectMapper objectMapper = new ObjectMapper();

    private UserDAO userDAO = UserDAO.getInstance();
    @Override
    public Handler register(){
        return (ctx)->{
            UserDTO newUser = ctx.bodyAsClass(UserDTO.class);
             User createdUser = userDAO.createUser(new User(newUser.getUsername(), newUser.getPassword()));
             Set<String> roles = createdUser.getRoles().stream().map(role->role.getName()).collect(Collectors.toSet());
             UserDTO returnUserDTO = new UserDTO(createdUser.getUsername(), roles);
            ctx.json(returnUserDTO);
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


    public UserDTO verifyToken(String token) throws Exception {
        //return null;

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Handler login(){
        return (ctx) -> {
            ObjectNode returnObject = objectMapper.createObjectNode(); // for sending json messages back to the client
            try {
                UserDTO user = ctx.bodyAsClass(UserDTO.class);
                UserDTO verifiedUser = userDAO.getVerifiedUser(user.getUsername(), user.getPassword());
                String token = createToken(verifiedUser);

                ctx.status(200).json(returnObject
                        .put("token", token)
                        .put("username", verifiedUser.getUsername()));

            } catch (EntityNotFoundException | ValidationException e) {
                ctx.status(401);
                System.out.println(e.getMessage());
                ctx.json(returnObject.put("msg", e.getMessage()));
            }
        };

    }

    private String createToken(UserDTO user) {
        try {
            String ISSUER;
            String TOKEN_EXPIRE_TIME;
            String SECRET_KEY;

            if (System.getenv("DEPLOYED") != null) {
                ISSUER = System.getenv("ISSUER");
                TOKEN_EXPIRE_TIME = System.getenv("TOKEN_EXPIRE_TIME");
                SECRET_KEY = System.getenv("SECRET_KEY");
            } else {
                ISSUER = Utils.getPropertyValue("ISSUER", "config.properties");
                TOKEN_EXPIRE_TIME = Utils.getPropertyValue("TOKEN_EXPIRE_TIME", "config.properties");
                SECRET_KEY = Utils.getPropertyValue("SECRET_KEY", "config.properties");
            }
            return tokenSecurity.createToken(user, ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(500, "Could not create token");
        }
    }
}
