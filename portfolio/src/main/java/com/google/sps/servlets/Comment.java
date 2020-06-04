package com.google.sps.servlets;
/**
* A class representing a comment. Holds some content, which is set upon initialization
*/
public class Comment{
  private String content;

  /**
  * Creates a new instance of a Comment from a string
  * @param content The string content of this comment
  */
  public Comment(String content){
    this.content = content;
  }

  public static String getDatastoreEntityKey(){
    return DATASTORE_ENTITY_KEY;
  }
}