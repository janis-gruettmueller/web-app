package main.java.com.spotify.app.api;

import main.java.com.spotify.app.model.User;
import main.java.com.spotify.app.service.UserService;
import main.java.com.spotify.app.service.UserServiceImpl;

// Tomcat 10.*.*
// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "userServlet", value = "/api/user")
public class UserServlet extends HttpServlet {

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
        // response.getWriter().write("User registered successfully");
    }

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
                // Invalid login credentials
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username/email or password");
                return;
            }

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }

        


        response.setStatus(HttpServletResponse.SC_OK);
        
    }
}
