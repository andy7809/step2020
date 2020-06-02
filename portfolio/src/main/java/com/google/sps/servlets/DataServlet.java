// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private static List<String> msgArray = new ArrayList<String>();


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    msgArray.add("Msg #1");
    msgArray.add("Msg #2");
    msgArray.add("msg #3");

    response.setContentType("text/html;");
    response.getWriter().println(objToJson(msgArray));
  }

  private static String objToJson(Object o){
    Gson gson = new Gson();
    String json = gson.toJson(o);
    return json;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try{
      String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
      System.out.println(test);
    } catch(Exception e){
      e.printStackTrace();
    }
    request.getParameter("text");
  }
}
