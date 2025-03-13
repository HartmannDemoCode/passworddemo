package dk.cphbusiness.security;

import dk.bugelhartmann.UserDTO;
import dk.cphbusiness.exceptions.ValidationException;
import dk.cphbusiness.persistence.HibernateConfig;
import dk.cphbusiness.security.entities.Role;
import dk.cphbusiness.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class UserDAO {
    private static UserDAO instance;
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private UserDAO() { }
    public static UserDAO getInstance() {
        if(instance==null){
            instance = new UserDAO();
        }
        return instance;
    }

    public User getUser(String username) {
        return null;
    }

    public User createUser(User user) {
        try(var em = emf.createEntityManager()){

            Set<Role> newRoleSet = new HashSet<>();
            if(user.getRoles().size() == 0){
                Role userRole = em.find(Role.class, "user");
                if(userRole == null){
                    userRole = new Role("user");
                    em.persist(userRole);
                }
                user.addRole(userRole);
            }
            user.getRoles().forEach(role->{
                Role foundRole = em.find(Role.class, role.getName());
                if(foundRole == null){
                    throw new EntityNotFoundException("No role found with that id");
                } else {
                    newRoleSet.add(foundRole);
                }
            });
            user.setRoles(newRoleSet);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch(Throwable ex){
            ex.printStackTrace();
        }
        return user;
    }
    public void createRole(Role role) {
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
        }
    }

    public UserDTO getVerifiedUser(String username, String password) throws ValidationException {
        try (EntityManager em = emf.createEntityManager()) {
            User user = em.find(User.class, username);
            if (user == null)
                throw new EntityNotFoundException("No user found with username: " + username); //RuntimeException
            user.getRoles().size(); // force roles to be fetched from db
            if (!user.verifyPassword(password))
                throw new ValidationException("Wrong password");
            return new UserDTO(user.getUsername(), user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()));
        }
    }

    public void deleteUser(String username) {
    }

    public void updateUser(User user) {
    }

    public static void main(String[] args) {
        UserDAO userDAO = UserDAO.getInstance();
        User user = new User("user1", "admin");
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        userDAO.createRole(userRole);
        userDAO.createRole(adminRole);
        userDAO.createUser(user);
    }
}
