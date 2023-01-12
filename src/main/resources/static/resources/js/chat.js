import sendPetitionInInterval from "./petition.js";

const username = window.location.pathname.split("/")[2];

sendPetitionInInterval('/api/messages/' + username, function (responseText) {
    const resultado = JSON.parse(responseText)
    const chats = document.getElementsByClassName("chat");
    console.log(chats);
    for (let i = 0; i < chats.length; i++) {
        const lis = resultado.messages.map((m) => createHtml(m.sender + (m.read ? '': '(Not Read)'), m.content, m.type, m.game));
        const res = '<div style="overflow-y: scroll; height: 200px;">' + lis.join("") + '</div>'
        if (chats[i].innerHTML !== res)
            chats[i].innerHTML = res;
    }
}, 1000)

function createHtml(sender, content, type, game)  {
    const notDangerContent = content.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
    const notDangerSender = sender.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
    if (type === 'INVITATION') {
        const notDangerGame = game.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
        return '<li>' + notDangerSender + ': Te ha invitado a jugar a ' + '<a href=' + notDangerContent + '>' + notDangerGame + '</a>' + '.</li>'
    }
    else if (type === 'CHAT')
        return '<li>' + notDangerSender + ': ' + notDangerContent + '</li>'
}
