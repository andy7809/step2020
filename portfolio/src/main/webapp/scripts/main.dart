// Main entry point for .dart scripts that are responsible for
// dynamic content management/creation.

import 'dart:html';
import 'dart:convert';
import 'package:intl/intl.dart';

final String collapsibleQueryStr = ".collapsible";
final String postBtnQueryStr = "#post";
final String commentQueryStr = "#comment";
final String commentWrapperQueryString = "#comment-wrapper";
final String selectQueryStr = "#cmnt-display-num";
final String clearQueryStr = "#clear";
final String fieldSetQueryStr = "#form-control";
final String loginMsgLiQueryStr = "#login-msg";
final String loginMsgLinkQueryStr = "#login-link";

void main() async{
  // Gets all collapsibles
  var collapsibles = querySelectorAll(collapsibleQueryStr);
  collapsibles.forEach(addCollapsibleClickListener);

  // Handle submit
  var submitBtn = querySelector(postBtnQueryStr);
  submitBtn.onClick.listen(submitComment);
  submitBtn.onClick.listen(displayComments);

  // Refresh comments on load
  window.onLoad.listen(displayComments);

  // Refresh comments when user selects different amount to show
  var selectElement = querySelector(selectQueryStr);
  selectElement.onChange.listen(displayComments);

  // Handle clear button press
  var clearBtn = querySelector(clearQueryStr);
  clearBtn.onClick.listen(deleteAllComments);

  // Get login info for this session
  var loginResp = await HttpRequest.getString("/login");
  var jsonLoginInfo = jsonDecode(loginResp);
  var isLoggedIn = jsonLoginInfo["isLoggedIn"];
  // If the user is logged in, enable the form. Otherwise, activate login link
  if(isLoggedIn) {
    enableForm();
  } else {
    setLoginLink(jsonLoginInfo["loginUrl"]);
  }
}

// Adds the handleCollapsibleClick handler to an element
void addCollapsibleClickListener(Element collapsible) {
  collapsible.onClick.listen(handleCollapsibleClick);
}

// Handles a click on collapsible event by toggling display of the next sibling of the collapsible
void handleCollapsibleClick(Event event) {
  var collapsibleElement = event.target as Element;
  var contentElement = collapsibleElement.nextElementSibling;
  if(contentElement.style.display == "block") {
    contentElement.style.display = "none";
  } else {
    contentElement.style.display = "block";
  }
}

// Handles a click on the submit button
Future<void> submitComment(Event event) async {
  var commentTextArea = querySelector(commentQueryStr) as TextAreaElement;
  var nickname = await getUserNickname();
  var commentContent = commentTextArea.value;
  var jsonCommentSubmit = { "comment": "$commentContent",
                            "nickname": "$nickname",
                            "time": "$getDateTimeNow()"};
  var request = new HttpRequest();
  request.open("POST", "/data");
  request.send(jsonCommentSubmit);
}

// Displays all comments in the DOM, should be called on load
Future<void> displayComments(Event event) async {
  var numCommentsToDisplay = getNumberOfCommentsToDisplay();
  var comments = await HttpRequest.getString("/data?num-comments=$numCommentsToDisplay");
  var responseJson = jsonDecode(comments);

  var commentWrapper = querySelector(commentWrapperQueryString);
  clearComments(commentWrapper);
  for(final comment in responseJson["commentList"]) {
    if (comment["content"].length > 0) {
      var commentDiv = new DivElement();
      commentDiv.text = comment["content"];
      commentDiv.classes.add("comment");
      commentWrapper.children.add(commentDiv);
    }
  }
}

// Gets the number of comments to display from select element
String getNumberOfCommentsToDisplay() {
  var selectElement = querySelector(selectQueryStr) as SelectElement;
  var selectedItem = selectElement.selectedOptions;
  return selectedItem[0].value;
}

// Clears all the children from an Element.
void clearComments(Element e) {
  e.children.clear();
}

// Deletes all comments from datastore
Future<void> deleteAllComments(Event event) async {
  var loginResp = await HttpRequest.getString("/login");
  var jsonLoginInfo = jsonDecode(loginResp);
  if (jsonLoginInfo["isAdminUser"]) {
    await HttpRequest.request("/delete-data", method: "POST");
    displayComments(new Event("Event"));
  } else {
    window.alert("You are not a website admin, please login to delete all comments");
  }
}

// Enables the comment posting form and removes the login message from the form
void enableForm() {
  var formFieldSet = querySelector(fieldSetQueryStr) as FieldSetElement;
  formFieldSet.disabled = false;
  var loginMsg = querySelector(loginMsgLiQueryStr);
  loginMsg.style.display = "none";
}

// Sets the login link href to the specified URL
void setLoginLink(String url) {
  var loginUrl = querySelector(loginMsgLinkQueryStr) as AnchorElement;
  loginUrl.href = url;
}

Future<String> getUserNickname() async {
  var loginResp = await HttpRequest.getString("/login");
  var jsonLoginInfo = jsonDecode(loginResp);
  var nickname = jsonLoginInfo["userNickname"];
  return nickname;
}

String getDateTimeNow() {
  var dateFormatter = new DateFormat.yMd().add_jm().format(new DateTime.now());
  return dateFormatter.toString();
}