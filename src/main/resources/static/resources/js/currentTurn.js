import sendPetitionInInterval from "/resources/js/petition.js";

const phase = window.location.pathname.split("/")[1];
sendPetitionInInterval("/api/turns", function(responseText) {
    const resultado = JSON.parse(responseText);
    const turn = resultado.turn;
    const playerInTurn = resultado.playerInTurn;
    const loggedPlayer = resultado.loggedPlayer;
    const bottoms = document.getElementsByClassName("nextTurn");
    const url1 = '\'/resources/images/jojo1.png\'';
    const url2 = '\'/resources/images/jojo2.png\'';
    const bottom = '<a href =  "/' + turn.phase + '/next">' +
        '<img src=' + url1 + ' alt="Texto alternativo" onmouseout="this.src=' + url1 + '" onmouseover="this.src=' + url2 + '">' +'</a>';
    if (loggedPlayer.id === playerInTurn.id) {
        for (let i = 0; i < bottoms.length; i++) {
            if (phase === "reestablishment")
                bottoms[i].innerHTML = '<a href =  "/' + phase + '/next">' +
                    '<img src=' + url1 + ' alt="Texto alternativo" onmouseout="this.src=' + url1 + '" onmouseover="this.src=' + url2 + '">' +'</a>';
            else if (turn.phase !== "start" || phase !== turn.phase)
                bottoms[i].innerHTML = bottom;
        }
    }
    else if (phase !== turn.phase)
        window.location.replace("/turns");
}, 1000);
