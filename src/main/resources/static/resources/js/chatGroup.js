import sendPetitionInInterval from "./petition.js";
sendPetitionInInterval('/api/messages/game', function (responseText) {
    const resultado = JSON.parse(responseText)
    const chats = document.getElementsByClassName("chatGroup");
    for (let i = 0; i < chats.length; i++) {
        const lis = resultado.messages.map((m) => createHtml(m.sender, m.content, m.type, m.game));
        chats[i].innerHTML = '<div style="overflow-y: scroll; height: 200px;">' + lis.join("") + '</div>'
    }
}, 1000)

function createHtml(sender, content, type, game)  {
    const notDangerContent = content.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
    if (type === 'GAME') {
        const notDangerSender = sender.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
        return '<li>' + notDangerSender + ': ' + notDangerContent + '</li>'
    }

    else if (type === 'ADVISE') {
        const notDangerGame = game.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
        return '<li>' + notDangerGame + ': ' + notDangerContent + '</li>'
    }

}
