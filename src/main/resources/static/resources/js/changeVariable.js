import sendPetitionInInterval from "./petition.js";

const phase = window.location.pathname.split("/")[2];
sendPetitionInInterval("/api/turns", function(responseText) {
    const resultado = JSON.parse(responseText)
    const turn = resultado.turn
    const playerInTurn = resultado.playerinTurn
    const loggedPlayer = resultado.loggedPlayer
    const bottoms = document.getElementsByClassName("nextTurn");
    for (let i = 0; i < bottoms.length; i++) {
        if ((playerInTurn === loggedPlayer && turn.phase !== "evade") || phase !== turn.phase) {
            bottoms[i].innerHTML = '<a href ="/"' + phase + '"/next">' + 'Next Turn' + '</a>';
        } else {
            bottoms[i].disabled = '';
        }
    }
}, 1000);

