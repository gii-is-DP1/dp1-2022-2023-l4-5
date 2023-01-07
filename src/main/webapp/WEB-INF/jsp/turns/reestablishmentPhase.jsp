<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<style>
    .pointer {
        display: flex;
        justify-content: center;
    }
</style>
<nt4h:layout pageName="Reestablishment Phase">
    <body class="background">
    <h1>Reestablishment Phase</h1>
    <h2>${game.currentPlayer}`s Turn</h2>
    <div class="container">
        <c:forEach items="${game.actualOrcs}" var="i">
            <img class="card-img-top" src="${i.enemy.frontImage}">
        </c:forEach>
    </div>
    <c:if test="${loggedPlayer.isNew()}">
        <form:form modelAttribute="turn" class="form-horizontal" id="choose-player-form">
            <div class="container">
                <div class="pointer">
                    <c:forEach var="i" begin="0" end="${player.deck.inHand.size()-1}">
                        <c:set var="abilityInGame" value="${player.deck.inHand[i]}" scope="page"/>
                        <div class="col-sm-2">
                            <nt4h:radioButtom name="currentAbility" element="${abilityInGame.id}" frontImage="${abilityInGame.ability.frontImage}" i="${i}0" image="/resources/images/muszka.png"/>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <button class="btn btn-default" type="submit">Discard Ability</button>
        </form:form>
    </c:if>
    <c:if test="${loggedPlayer.isNew()}">
        <div class="container">
            <div class="display: flex; justify-content: center;">
                <c:forEach var="i" begin="0" end="${player.deck.inHand.size()-1}">
                    <c:set var="abilityInGame" value="${player.deck.inHand[i]}" scope="page"/>
                    <div class="col-sm-2">
                        <img src="${abilityInGame.ability.frontImage}">
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <div class="row">
        <div class="chatGroup"></div>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </c:if>
    </div>
    <div class="nextTurn"></div>
    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/currentTurn.js" type="module"></script>

</nt4h:layout>

<style>
    .background {
        background-image: url("/resources/images/campo.jpg");
        background-repeat: no-repeat;
        background-size: cover;
    }
</style>


