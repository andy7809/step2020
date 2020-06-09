// Main entry point for .dart scripts that are responsible for
// dynamic content management/creation.

import 'dart:html';

final String collapsibleQueryString = '.collapsible';

void main() {
  // Gets all collapsibles
  ElementList<Element> collapsibles = querySelectorAll(collapsibleQueryString);
  collapsibles.forEach(addCollapsibleClickListener);
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