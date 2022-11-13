<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="lobby">
    <h2>Game Lobby</h2>
    <c:forEach items="${selections}" var="player">
        <h1>"${player.name}"</h1>
        <form:form modelAttribute="p" class="form-horizontal">
            <button class="btn btn-default" type="submit">
                <input name="a" value="${selections}" type="hidden"/>
                <c:choose>
                    <c:when test="${player.ready}">
                        Ready!
                    </c:when>
                    <c:otherwise>
                        Not Ready
                    </c:otherwise>
                </c:choose>
            </button>
        </form:form>
    </c:forEach>
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

    reset('/messages/update/' + username, function (responseText) {
        const resultado = JSON.parse(responseText)
        chat = document.getElementById("patata");
        lis = resultado.messages.map(function (m) {
            return "<li>" + m + "</li>"
        })
        chat.innerHTML = lis.join("")
    })
</script>
