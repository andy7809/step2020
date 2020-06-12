package com.google.sps.servlets;
/**
* A class representing a comment. Holds some content, which is set upon initialization
*/
public class Comment{
  private String content;
  private String name;

  /**
  * Creates a new instance of a Comment from a string
  * @param content The string content of this comment
  * @param name The name of the user posting this comment
  */
  public Comment(String content, String name){
    this.content = content;
    this.name = name;
  }
}