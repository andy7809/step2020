"use strict";
let popupContentMap = new Map();
popupContentMap.set("philosophy", "Content")
document.addEventListener("click", (event) => respondToEvent(event));

function respondToEvent(event){
    let clickDisplayKey = event.target.getAttribute("displayKey");
    if(clickDisplayKey == null)
    {
        return;
    }
    let sectionId = event.target.parentNode.parentNode.parentNode.id;
    let popupDiv = document.getElementById(sectionId.concat("Popup"));
    popupDiv.innerHTML = popupContentMap.get(clickDisplayKey);
    popupDiv.style.visibility = "visible";
}
