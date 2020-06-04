/**
 A script that adds on click functionality to a set of buttons on my webpage
 @author Andrew Wiedenmann
 */
"use strict";

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

let submitButton = document.getElementById("post");
submitButton.addEventListener("click", postComment);

/**
  Responds to the click of the post button. Posts a comment to the server.
 */
function postComment() {
  const commentVal = document.getElementById("comment").value;
  const data = { comment: commentVal };
  HttpRequestBody = {
    method: "POST",
    body: JSON.stringify(data),
  };
  fetch("/data", HttpRequestBody);
}

/**
  Retrieves all comments from the server and displays them on the page.
 */
async function getComments() {
  let commentWrapper = document.getElementById("comment-wrapper");
  const response = await fetch("/data");
  let serverResponse = await response.json();
  for (const comment in serverResponse["commentList"]) {
    const commentObj = serverResponse["commentList"][comment];
    //Because an arraylist is used for now in server, sometimes there are empty objects in response
    if (commentObj["content"].length > 0) {
      let commentDiv = document.createElement("div");
      let commentText = document.createTextNode(content["content"]);
      commentDiv.appendChild(commentText);
      commentDiv.className = "comment";
      commentWrapper.appendChild(commentDiv);
    }
  }
}

window.onload = getComments;
