package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.google.sps.servlets.Comment;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

/**
* DataServlet class for handling HTTP responses and requests.
*/
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  //Default value "Comment" for testing purposes
  private String commentDatastoreKey = "Comment";
  /**
  * Handles HTTP get request. Get all comments from the datastore and send as list json in response.
  * @param request the request received by servlet.
  * @param response HTTP response to GET request.
  */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query(commentDatastoreKey);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      String content = (String) entity.getProperty("content");
      comments.add(new Comment(content));
    }

    response.setContentType("text/html;");
    response.getWriter().println("{\"commentList\": " + objToJson(comments) + "}");
  }

  private static String objToJson(Object o){
    Gson gson = new Gson();
    String json = gson.toJson(o);
    return json;
  }

  /**
  * Handles HTTP post request. Create a json from the request body and add it to the datastore.
  * @param request the request received by servlet.
  * @param response HTTP response to POST request.
  */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try{
      String requestBody = getRequestBody(request);
      JSONObject jsonComment = new JSONObject(requestBody);
      Entity commentEntity = new Entity(commentDatastoreKey);
      commentEntity.setProperty("content", jsonComment.get("comment"));
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  private String getRequestBody(HttpServletRequest request){
    try{
      String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
      return requestBody;
    } catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

  public void setCommentDatastoreKey(String newKey){
    commentDatastoreKey = newKey;
  }

  public String getCommentDatastoreKey(){
    return commentDatastoreKey;
  }
}
