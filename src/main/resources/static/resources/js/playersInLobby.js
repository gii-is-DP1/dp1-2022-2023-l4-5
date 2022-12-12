import sendPetitionInInterval from "./petition.js";

sendPetitionInInterval('/api/games', function (responseText) {
    const resultado = JSON.parse(responseText);
    const readys = document.getElementsByClassName("ready");
    const nexts = document.getElementsByClassName("next");
    const lis = resultado.game.players.map(function (player) {
        const namePlayer = player.name;
        const nameHeroes = player.heroesInGame.sort().map(h => h.hero.name).join(", ")
        const ready = player.ready ? "Ready" : "Not ready";
        const actions = resultado.loggedUser.player.host ? " - " + "<a href='/games/deletePlayer/" + player.id + "'>Get Out!!</a>" : "";
        return createHtml(namePlayer, nameHeroes,ready,actions);
    })
    for (let ready in readys) {
        ready.innerHTML = lis.join("");
    }
    const timer = resultado.timer;
    for (let next in nexts) {
        if (timer > 0) next.innerHTML = "<h1>The game will start in " + timer + " seconds</h1>";
        else if (game.players.size() < game.maxPlayers) next.innerHTML = "<h1>Waiting for players</h1>";
        else next.innerHTML = "<a href='/games/selectOrder/'>Continue</a>";
    }
}, 1000);

function createHtml(namePlayer, nameHeroes, ready, actions) {
    return "<li>" + namePlayer + " {" + (nameHeroes === "" ? "No hero selected" : nameHeroes) + "} " + " " + ready + actions + "</li>"
}
