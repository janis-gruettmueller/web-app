package main.java.com.spotify.app.api;

import main.java.com.spotify.app.model.User;
import main.java.com.spotify.app.service.UserService;
import main.java.com.spotify.app.service.UserServiceImpl;

/** for Tomcat 10.*.*
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "userServlet", value = "/api/user")
public class UserServlet extends HttpServlet {

    private final int MAX_IDLE_TIME = 30;
    private final Integer MAX_LOGIN_ATTEMPS = 5;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "signUp":
                    handleSignUp(request, response);
                    break;
                case "login":
                    handleLogin(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
                    break;
            }
        } catch (IllegalArgumentException | SQLException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void handleSignUp(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username == null || email == null || password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        userService.registerUser(username, email, password);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write("User registered successfully");
    }

    /**
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String loginIdentifier = request.getParameter("loginIdentifier");
        String password = request.getParameter("password");

        if (loginIdentifier == null || password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        try {
            User authenticatedUser = userService.authenticateUser(loginIdentifier, password);
            
            if (authenticatedUser == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid login details!");
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("user", authenticatedUser.getUsername());
            session.setMaxInactiveInterval(60 * this.MAX_IDLE_TIME);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Login successful!");
        

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }


        response.setStatus(HttpServletResponse.SC_OK);
    }
    */

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String loginIdentifier = request.getParameter("loginIdentifier");
        String password = request.getParameter("password");

        if (loginIdentifier == null || password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }
        
        try {
            User user = userService.getUser(loginIdentifier);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid login details!");
                return;
            }

            if (user.isLocked()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Account is locked!");
                return;
            }
            
            if (userService.authenticateUser(user, password) == false) {
                userService.incrementNumFailedAttempts(user.getId());

                if (user.getNumFailedAttempts() + 1 >= this.MAX_LOGIN_ATTEMPS) {
                    userService.lockUser(user.getId());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Account is locked due to too many failed attempts!");
                    return;
                }

                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid login details!");
                return;
            }

            userService.resetNumFailedAttempts(user.getId());
            HttpSession session = request.getSession(true);

            session.setAttribute("user", user.getUsername());
            session.setMaxInactiveInterval(60 * this.MAX_IDLE_TIME);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Login successful!");

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
}
