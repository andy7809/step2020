package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Servlet class to handle requests for deleting all data from datastore.
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
    boolean isLoggedIn = userService.isUserLoggedIn();
    boolean isAdminUser = false;
    String userNickname = "";
    if (isLoggedIn) {
      isAdminUser = userService.isUserAdmin();
      userNickname = userService.getCurrentUser().getNickname();
    }

    response.setContentType("text/html;");
    String returnJson = String.format("{\"isLoggedIn\": %s, \"loginUrl\": \"%s\", \"isAdminUser\": %s, \"userNickname\": \"%s\"}",
                        isLoggedIn, userService.createLoginURL(urlToRedirectToAfterUserLogsIn), isAdminUser, userNickname);
    response.getWriter().println(returnJson);
  }
}