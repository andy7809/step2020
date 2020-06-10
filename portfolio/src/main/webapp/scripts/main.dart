// Main entry point for .dart scripts that are responsible for
// dynamic content management/creation.

import 'dart:html';
import 'dart:convert';

final String collapsibleQueryString = ".collapsible";
final String submitBtnQueryString = "#post";

void main() {
  // Gets all collapsibles
  ElementList<Element> collapsibles = querySelectorAll(collapsibleQueryString);
  collapsibles.forEach(addCollapsibleClickListener);

  Element submitBtn = querySelector(submitBtnQueryString);
  submitBtn.onClick.listen(submitComment);
}

// Adds the handleCollapsibleClick handler to an element
void addCollapsibleClickListener(Element collapsible) {
  collapsible.onClick.listen(handleCollapsibleClick);
}

// Handles a click on collapsible event by toggling display of the next sibling of the collapsible
void handleCollapsibleClick(Event event) {
  Element collapsibleElement = event.target;
  Element contentElement = collapsibleElement.nextElementSibling;
  if(contentElement.style.display == "block") {
    contentElement.style.display = "none";
  } else {
    contentElement.style.display = "block";
  }
}

void submitComment(Event event) {
  var commentTextArea = querySelector("#comment") as TextAreaElement; 
  print(commentTextArea.runtimeType);
  String commentVal = commentTextArea.value;
  print(commentVal);
  var json = { 'comment': commentVal };
  var request = new HttpRequest();
  request.open("POST", "/data");
  request.send(json);
  print("done");
}