// Main entry point for .dart scripts that are responsible for
// dynamic content management/creation.

import 'dart:html';
import 'dart:convert';

final String collapsibleQueryStr = ".collapsible";
final String postBtnQueryStr = "#post";

void main() {
  // Gets all collapsibles
  var collapsibles = querySelectorAll(collapsibleQueryStr);
  collapsibles.forEach(addCollapsibleClickListener);

  var submitBtn = querySelector(postBtnQueryStr);
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
  var json = { 'comment': commentTextArea.value };
  var request = new HttpRequest();
  request.open("POST", "/data");
  request.send(json);
}

// Displays all comments in the DOM, should be called on load
Future<void> displayComments(Event event) async {
  var commentWrapper = querySelector("#comment-wrapper");
  var response = await HttpRequest.getString("/data");
  var responseJson = jsonDecode(response);
  for(final comment in responseJson["commentList"]) {
    if (comment["content"].length > 0) {
      var commentDiv = new DivElement();
      commentDiv.text = comment["content"];
      commentDiv.classes.add("comment");
      commentWrapper.children.add(commentDiv);
    }
  }
}
