/**
 A script that adds on click functionality to a set of buttons on my webpage
 @author Andrew Wiedenmann
 */
"use strict";

// Get all buttons. coll is an array of html elements
let coll = document.getElementsByClassName("collapsible");
let i;

for (i = 0; i < coll.length; i++) {
  // Add onclick functionality to every item in coll
  coll[i].addEventListener("click", function() {
    // invert active (active -> inactive or inactive -> inactive)
    this.classList.toggle("active");
    let content = this.nextElementSibling;
    // If showing, unshow. Else, if not showing, show
    if (content.style.display === "block") {
      content.style.display = "none";
    } else {
      content.style.display = "block";
    }
  });
}