package dk.cphbusiness.security;

import dk.cphbusiness.persistence.HibernateConfig;
import dk.cphbusiness.security.entities.Role;
import dk.cphbusiness.security.entities.User;
import jakarta.persistence.EntityManagerFactory;

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

    public void createUser(User user) {
        try(var em = emf.createEntityManager()){

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
    }
    public void createRole(Role role) {
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
        }
    }

    public void deleteUser(String username) {
    }

    public void updateUser(User user) {
    }

    public static void main(String[] args) {
        UserDAO userDAO = UserDAO.getInstance();
        User user = new User("user1", "admin");
        Role role = new Role("user");
        user.addRole(role);
        userDAO.createRole(role);
        userDAO.createUser(user);
    }
}
