import sendPetitionInInterval from "./petition.js";

const username = window.location.pathname.split("/")[2];


sendPetitionInInterval('/api/messages/' + username, function (responseText) {
    const resultado = JSON.parse(responseText)
    const chats = document.getElementsByClassName("chat");
    for (const chat in chats) {
        const lis = resultado.messages.map((m) => createHtml(m.sender, m.content));
        chat.innerHTML = lis.join("")
    }
}, 1000)

function createHtml(sender, content)  {
    return "<div class='chat'> + sender + content + '</div>'"
}
