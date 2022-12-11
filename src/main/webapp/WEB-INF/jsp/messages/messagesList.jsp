<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->


<nt4h:layout pageName="chat">
    <h2>"Chatea con ${receiver}"</h2>
    <div class="row">
        <div id="chat">
            <c:forEach items="${messages}" var="m">
                <li>${m.sender.username}: ${m.content}</li>
            </c:forEach>
        </div>
        <form:form modelAttribute="chat" class="form-horizontal">
            <nt4h:inputField label="Content" name="content"/>
        </form:form>

    </div>
</nt4h:layout>

<script type="module">
    import sendPetitionInInterval from "../../../../resources/js/petition.js";

    const username = window.location.pathname.split("/")[2];
    console.log(username);
    sendPetitionInInterval('/api/messages/' + username, function (responseText) {
        const resultado = JSON.parse(responseText)
        const chat = document.getElementById("chat");
        const lis = resultado.messages.map(function (m) {
            return "<li class='message'>" + m.sender + ": " + m.content + "</li>"
        });
        chat.innerHTML = lis.join("")
    }, 1000)
</script>
