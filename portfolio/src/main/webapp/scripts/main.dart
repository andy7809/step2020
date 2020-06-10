// Main entry point for .dart scripts that are responsible for
// dynamic content management/creation.

import 'dart:html';
import 'dart:convert';

final String COLLAPSIBLE_QUERY_STR = ".collapsible";
final String SUBMIT_BTN_QUERY_STR = "#post";

void main() {
  // Gets all collapsibles
  var collapsibles = querySelectorAll(COLLAPSIBLE_QUERY_STR);
  collapsibles.forEach(addCollapsibleClickListener);

  var submitBtn = querySelector(SUBMIT_BTN_QUERY_STR);
  submitBtn.onClick.listen(submitComment);
}

// Adds the handleCollapsibleClick handler to an element
void addCollapsibleClickListener(Element collapsible) {
  collapsible.onClick.listen(handleCollapsibleClick);
}

// Handles a click on collapsible event by toggling display of the next sibling of the collapsible
void handleCollapsibleClick(Event event) {
  var collapsibleElement = event.target;
  var contentElement = collapsibleElement.nextElementSibling;
  if(contentElement.style.display == "block") {
    contentElement.style.display = "none";
  } else {
    contentElement.style.display = "block";
  }
}

void submitComment(Event event) {
  var commentTextArea = querySelector("#comment") as TextAreaElement; 
  var commentVal = commentTextArea.value;
  var json = { 'comment': commentVal };
  var request = new HttpRequest();
  request.open("POST", "/data");
  request.send(json);
}