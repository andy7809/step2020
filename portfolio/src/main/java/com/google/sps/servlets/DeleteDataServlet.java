package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Servlet class to handle requests for deleting all data from datastore.
*/
@WebServlet("/delete-data")
public class DeleteDataServlet extends HttpServlet {
  //Default value "Comment" for testing purposes
  private String commentDatastoreKey = "Comment";

  /**
  * Handles HTTP post request. Delete all comments from the datastore.
  * @param request the request received by servlet.
  * @param response HTTP response to POST request.
  */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query(commentDatastoreKey);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Key> keys = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      keys.add(entity.getKey());
    }
    datastore.delete(keys);
  }

  public void setCommentDatastoreKey(String newKey) {
    commentDatastoreKey = newKey;
  }

  public String getCommentDatastoreKey() {
    return commentDatastoreKey;
  }
}
