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
            <c:when test="${player.ready}">
                <h1>You are ready!</h1>
            </c:when>
            <c:otherwise>
                <form:form modelAttribute="player" class="form-horizontal" id="add-game-form">
                    <button class="btn btn-default" type="submit">Add hero!</button>
                </form:form>
            </c:otherwise>
        </c:choose>
    </div>
    <div id="next"></div>

</nt4h:layout>

<script type="module">
    import sendPetitionInInterval from "../../../../resources/js/petition.js";

    sendPetitionInInterval('/api/games', function (responseText) {
        const resultado = JSON.parse(responseText);
        const player = document.getElementById("ready");
        const next = document.getElementById("next");
        const lis = resultado.game.players.map(function (player) {
            const namePlayer = player.name;
            console.log(player.heroesInGame)
            const nameHeroes = player.heroesInGame.sort().map(function (h) {return h.hero.name;}).join(", ")
            console.log(player.ready)
            const ready = player.ready ? "Ready" : "Not ready";
            const actions = resultado.loggedUser.player.host ? " - " + "<a href='/games/deletePlayer/" + player.id + "'>Get Out!!</a>" : "";
            console.log(namePlayer + " " + nameHeroes + " " + ready + " " + actions);
            return "<li>" + namePlayer + " {" + (nameHeroes === "" ? "No hero selected": nameHeroes)  + "} " + " " + ready + actions + "</li>"
        })
        player.innerHTML = lis.join("");
        const timer = resultado.timer;
        if (timer > 0) next.innerHTML = "<h1>The game will start in " + timer + " seconds</h1>";
        else next.innerHTML = "<a href='/games/selectOrder/'>Continue</a>";

        }, 1000);
</script>
