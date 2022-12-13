import sendPetitionInInterval from "./petition.js";

const username = window.location.pathname.split("/")[2];

sendPetitionInInterval('/api/messages/' + username, function (responseText) {
    const resultado = JSON.parse(responseText)
    const chats = document.getElementsByClassName("chat");
    console.log(chats);
    for (let i = 0; i < chats.length; i++) {
        // TODO: mejorar.
        const lis = resultado.messages.map((m) => createHtml(m.sender, m.content));
        chats[i].innerHTML = lis.join("")
    }
}, 1000)

function createHtml(sender, content)  {
    return '<li>' + sender + ': ' + content + '</li>'
}
