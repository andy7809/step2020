//A simple script to update the content of an html element with facts about me
//depending on which element is clicked by a user
//Author: Andrew Wiedenmann
"use strict";

//A map of displayKey values mapped to html string content. When an element is clicked, the string corresponding to that
//displayKey value is displayed on the webpage
let popupContentMap = new Map();

let PHILOSOPHY_STR_CONTENT = "Right now, I'm reading  'On the Aesthetic Education of Man' by Friedrich Schiller. It's where I found the quote at the top of the page.";
let BOARD_GAMES_STR_CONTENT = "In quarantine, I've been playing Betrayal and Catan recently, which are both table-top games. My favorite casual games are Codenames and Blokus - both have online versions, so if you ever want to play a game, let me know!";
let MOVIES_STR_CONTENT = "I have been on a horror movie kick recently - I just watched Hush, The Ring, and Netflix's The Platform";
let TV_SHOWS_STR_CONTENT = "I enjoy Parks and Rec, M*A*S*H, King of the Hill, and I recently started watching Aziz Ansari's Master of None";
let MUSIC_STR_CONTENT = "Top 5: Dunno - Mac Miller, Summerhouse - Kota the Friend, Fishing for Fishies - King Gizzard, Volcano - Jimmy Buffett, Woods - Bon Iver";
let DRONES_STR_CONTENT = "On campus, I am a research assistant to a PhD student working on drone swarms. During the school year, I work about 10-15 hours a week in the lab coding, gathering data, or helping with data visualization. I am also a member of my school's UAV robotics club, where I work mostly with control systems";
let CODING_STR_CONTENT = "I have taken two college classes in Java about algorithms and data structures. In my work as a research assistant, I code in C++ and python.";
let WEB_DEV_STR_CONTENT = "And, as part of this project, I learned HTML, CSS and Javascript";
let MACHINE_LEARNING_STR_CONTENT = "Recently, I have been learning tensorflow to apply it to my research project.";

popupContentMap.set("philosophy", PHILOSOPHY_STR_CONTENT);
popupContentMap.set("boardGames", BOARD_GAMES_STR_CONTENT);
popupContentMap.set("movies", MOVIES_STR_CONTENT);
popupContentMap.set("tvShows", TV_SHOWS_STR_CONTENT);
popupContentMap.set("music", MUSIC_STR_CONTENT);
popupContentMap.set("drones", DRONES_STR_CONTENT);
popupContentMap.set("coding", CODING_STR_CONTENT);
popupContentMap.set("machineLearning", MACHINE_LEARNING_STR_CONTENT);
popupContentMap.set("webDev", WEB_DEV_STR_CONTENT);

document.addEventListener("click", (event) => respondToEvent(event));

function respondToEvent(event) {
    let clickDisplayKey = event.target.getAttribute("displayKey");
    if (clickDisplayKey == null) {
        return;
    }
    let sectionId = event.target.parentNode.parentNode.parentNode.id;
    let popupDiv = document.getElementById(sectionId.concat("Popup"));
    popupDiv.innerHTML = popupContentMap.get(clickDisplayKey);
    popupDiv.style.visibility = "visible";
}
