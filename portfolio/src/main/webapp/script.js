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
    coll[i].addEventListener("click", respondToClick);
}

function respondToClick(clickEvent){
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