import sendPetitionInInterval from "./petition.js";
sendPetitionInInterval('/api/messages/game', function (responseText) {
    const resultado = JSON.parse(responseText)
    const chats = document.getElementsByClassName("chatGroup");
    for (let i = 0; i < chats.length; i++) {
        const lis = resultado.messages.map((m) => createHtml(m.sender, m.content));
        chats[i].innerHTML = lis.join("")
    }
}, 1000)

function createHtml(sender, content)  {
    return '<li>' + sender + ': ' + '<pre>' + '<code>' + content.replaceAll("<", "&lt;").replaceAll(">", "&gt;") + '</code>' + '</pre>' + '</li>'
}
