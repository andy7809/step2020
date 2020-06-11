// Main entry point for .dart scripts that are responsible for
// dynamic content management/creation.

import 'dart:html';
import 'dart:convert';

final String COLLAPSIBLE_QUERY_STR = ".collapsible";
final String POST_BTN_QUERY_STR = "#post";
final String SELECT_QUERY_STR = "#cmnt-display-num";

void main() {
  // Gets all collapsibles
  var collapsibles = querySelectorAll(COLLAPSIBLE_QUERY_STR);
  collapsibles.forEach(addCollapsibleClickListener);

  var submitBtn = querySelector(POST_BTN_QUERY_STR);
  submitBtn.onClick.listen(submitComment);

  window.onLoad.listen(displayComments);
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
void submitComment(Event event) {
  var commentTextArea = querySelector("#comment") as TextAreaElement; 
  var commentVal = commentTextArea.value;
  var json = "{comment: $commentVal}";
  var request = new HttpRequest();
  request.open("POST", "/data");
  request.send(json);
}

// Displays all comments in the DOM, should be called on load
Future<void> displayComments(Event event) async {
  var numCommentsToDisplay = getNumberOfCommentsToDisplay();
  var comments = await HttpRequest.getString("/data?num-comments=5");
  var responseJson = jsonDecode(comments);
  print(responseJson);

  var commentWrapper = querySelector("#comment-wrapper");
  for(final comment in responseJson["commentList"]) {
    if (comment["content"].length > 0) {
      var commentDiv = new DivElement();
      commentDiv.text = comment["content"];
      commentDiv.classes.add("comment");
      commentWrapper.children.add(commentDiv);
    }
  }
}

String getNumberOfCommentsToDisplay() {
  var selectElement = querySelector(SELECT_QUERY_STR) as SelectElement;
  var selectedItem = selectElement.selectedOptions;
  return selectedItem[0].value;
}