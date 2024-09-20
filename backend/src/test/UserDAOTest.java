package test;

import java.sql.SQLException;

import main.java.com.spotify.app.model.User;
import main.java.com.spotify.app.service.UserService;
import main.java.com.spotify.app.service.UserServiceImpl;

public class UserDAOTest {

    public static boolean testRegisterUser(UserService userService) {    
        try {
            userService.registerUser("testUser1", "testUser1@mail.com", "TestP@ssw0rd");
        } catch (IllegalArgumentException | SQLException e) {
            return false;
        }

        return true;
    }

    public static boolean testGetUser(UserService userService) {
        try {
            User testUser = null;
            
            testUser = userService.getUser("testUser1@mail.com");
            if (testUser == null) {
                return false;
            }

            testUser = userService.getUser("testUser1");
            if (testUser == null) {
                return false;
            }

            testUser = userService.getUser("testUser");
            if (testUser != null) {
                return false;
            }
        } catch (IllegalArgumentException | SQLException e) {
            return false;
        }

        return true;
    }

    public static boolean testAuthenticateUser(UserService userService) {
        try {
            String result = null;

            result = userService.authenticateUser("testUser1", "TestP@ssw0rd");
            if (result != "sessionToken") {
                return false;
            }

        } catch (IllegalArgumentException | SQLException e) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) throws IllegalArgumentException, SQLException {
        UserService userService = new UserServiceImpl();

        //userService.registerUser("testUser1", "testUser1@mail.com", "TestP@ssw0rd");
        
        System.out.println("user registration: " + testRegisterUser(userService));
        System.out.println("user authentification: " + testAuthenticateUser(userService));
        System.out.println("user details: " + testGetUser(userService));
    }
}
