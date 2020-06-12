package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Servlet class to handle requests for deleting all data from datastore
*/
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  /**
  * Handles HTTP post request. Delete all comments from the datastore.
  * @param request the request received by servlet.
  * @param response HTTP response to POST request.
  */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    String urlToRedirectToAfterUserLogsIn = "/";
    response.setContentType("text/html;");
    response.getWriter().println("{\"isLoggedIn\": " + userService.isUserLoggedIn() + ", \"loginUrl\": \"" + userService.createLoginURL(urlToRedirectToAfterUserLogsIn) + "\"}");
  }
}