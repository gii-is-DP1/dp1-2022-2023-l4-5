import sendPetitionInInterval from "../../../../resources/js/petition.js";

sendPetitionInInterval('/api/games/ready/', function (responseText) {
    const resultado = JSON.parse(responseText)
    const form = document.getElementById("waiting");
    if (resultado.isReady&& form.hasAttribute("hidden"))
        form.removeAttribute("hidden")
    else if (!resultado.isReady && !form.hasAttribute("hidden"))
        form.setAttribute("hidden", "hidden")
}, 1000)
