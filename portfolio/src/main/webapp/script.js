/**
 A script that adds on click functionality to a set of buttons on my webpage
 @author Andrew Wiedenmann
 */
"use strict";
const POST_TARGET = "post";
const COMMENT_TARGET = "comment";

// Get all collapsibles. coll is an array of html elements
let coll = document.getElementsByClassName("collapsible");
let i;

for (i = 0; i < coll.length; i++) {
  // Add onclick functionality to every item in coll
  coll[i].addEventListener("click", collapsibleRespondToClick);
}

/**
 Designed to give HTML elements of the collapsible class the ability to respond to click events.
 Responds to click by toggling the style.display property of the next element sibling in the DOM.
 @param clickEvent a clickEvent object on a collapsible item
 */
function collapsibleRespondToClick(clickEvent) {
  //The element that was clicked
  const clickedElement = clickEvent.target;
  //The next element in the HTML, which is the content div containing the content in the collapsible
  let content = clickedElement.nextElementSibling;
  // If showing (display is block), hide. Else, set display to block.
  if (content.style.display === "block") {
    content.style.display = "none";
  } else {
    content.style.display = "block";
  }
}

let submitButton = document.getElementById(POST_TARGET);
submitButton.addEventListener("click", postComment);

function postComment(clickEvent){
  const data = {'cont': document.getElementById(COMMENT_TARGET).value};
  fetch("/data", {
    method: "POST", 
    body: JSON.stringify(data)
  });
}