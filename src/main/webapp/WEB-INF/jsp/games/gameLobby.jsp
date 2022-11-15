<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="lobby">
    <h2>Game Lobby</h2>
    <div id="ready">

    </div>

    <div>
        <c:choose>
            <c:when test="${!player.ready}">
                <form:form modelAttribute="player" class="form-horizontal" id="add-game-form">
                <button class="btn btn-default" type="submit">Join to this lobby</button>
                </form:form>
            </c:when>
                <c:otherwise>
                    <form:form modelAttribute="player" class="form-horizontal" id="add-game-form">
                    <button class="btn btn-default" type="submit">Ready?</button>
                    </form:form>
                </c:otherwise>
        </c:choose>
    </div>
</nt4h:layout>

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
    gameId = window.location.pathname.split("/")[2];

    reset('/games/update/' + gameId, function (responseText) {
        const resultado = JSON.parse(responseText)
        chat = document.getElementById("ready");
        lis = resultado.messages.map(function (m) {
            return "<li>" + m + "</li>"
        })
        chat.innerHTML = lis.join("")
    })
</script>
