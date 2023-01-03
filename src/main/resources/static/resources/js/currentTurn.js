import sendPetitionInInterval from "/resources/js/petition.js";

const phase = window.location.pathname.split("/")[1];
sendPetitionInInterval("/api/turns", function(responseText) {
    const resultado = JSON.parse(responseText)
    const turn = resultado.turn
    const playerInTurn = resultado.playerInTurn
    const loggedPlayer = resultado.loggedPlayer
    const bottoms = document.getElementsByClassName("nextTurn");
    for (let i = 0; i < bottoms.length; i++) {
        if ((playerInTurn.name === loggedPlayer.name && turn.phase !== "start") || phase !== turn.phase) {
            bottoms[i].innerHTML = '<a href =  "/' + turn.phase + '/next">' + 'Next Turn' + '</a>';
        } else {
            bottoms[i].innerHTML = '';
        }
    }
}, 1000);
