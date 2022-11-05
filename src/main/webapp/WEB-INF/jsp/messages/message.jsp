<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->


<petclinic:layout pageName="chat">
    <h2>"Chatea con ${receiver}"</h2>
    <div class="row">
        <div id="chat">
            <c:forEach items="${messages}" var="m">
                <li>${m.sender.username}: ${m.content}</li>
            </c:forEach>
        </div>
        <form:form modelAttribute="chat" class="form-horizontal">
            <petclinic:inputField label="Content" name="content"/>
            <!---
            <button class="btn btn-default" type="submit">Send</button>
            -->
        </form:form>

    </div>
</petclinic:layout>

<script type="text/javascript">

    function reset(url, action) {
        $(document).ready(function () {
            setInterval(function () {
                const http = new XMLHttpRequest();
                http.open('GET', url, true);
                http.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        action(this.responseText)
                    }
                }
                http.send()
            }, 1000);
        });
    }

    console.log("hola")
    username = window.location.pathname.split("/")[2];

    reset('/message/update/' + username, function (responseText) {
        const resultado = JSON.parse(responseText)
        chat = document.getElementById("chat");
        lis = resultado.messages.map(function (m) {
            return "<li>" + m + "</li>"
        })
        chat.innerHTML = lis.join("")
    })
</script>
