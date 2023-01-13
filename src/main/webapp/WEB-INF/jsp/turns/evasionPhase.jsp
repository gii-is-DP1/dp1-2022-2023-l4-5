<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Hero Attack Action">
    <h1>Turno del jugador ${game.currentPlayer}</h1>
    <style>
        .card-img-top {
            width: 100%;
            padding-top: 2rem;
            padding-bottom: 2rem;
        }

        .pointer {
            display: flex;
            justify-content: center;
        }
    </style>
    <div class="container">
        <c:forEach var="i" begin="0" end="${currentPlayer.health}">
            <img src="/resources/images/heart_hero.gif" width="50" height="50">
        </c:forEach>
        <p style="font-size:5rem">
            <img src="/resources/images/gloria.gif" width="50" height="50"> ${currentPlayer.statistic.glory}
            <img src="/resources/images/coin.gif" width="50" height="50"> ${currentPlayer.statistic.gold}
        </p>
    </div>
    <c:if test="${!loggedPlayer.isNew()}">
        <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
            <div class="container">
                <c:if test="${currentPlayer.deck.inHand.size()!=0}">
                    <div class="pointer">
                        <c:forEach var="i" begin="0" end="${currentPlayer.deck.inHand.size()-1}">
                            <c:set var="abilityInGame" value="${currentPlayer.deck.inHand[i]}" scope="page"/>
                            <div class="col-sm-2">
                                <nt4h:radioButtom name="currentAbility" element="${abilityInGame.id}"
                                                  frontImage="${abilityInGame.ability.frontImage}" i="${i}0"
                                                  image="/resources/images/muszka.png"/>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${currentPlayer.deck.inHand.size()==0}">
                    <div style="display: flex; justify-content: center;">
                        <c:out value="There are no abilities in hand."/>
                    </div>
                </c:if>
            </div>
            <c:if test="${currentPlayer.deck.inHand.size()!=0}">
                <button class="btn btn-default" type="submit">Discard</button>
            </c:if>
        </form:form>
    </c:if>
    <c:if test="${loggedPlayer.isNew()}">
        <div class="container">
            <div style="display: flex; justify-content: center;">
                <c:if test="${currentPlayer.deck.inHand.size()!=0}">
                    <c:forEach var="i" begin="0" end="${currentPlayer.deck.inHand.size()-1}">
                        <c:set var="abilityInGame" value="${currentPlayer.deck.inHand[i]}" scope="page"/>
                        <div class="col-sm-2">
                            <img src="${abilityInGame.ability.frontImage}" width="90%" height="90%">
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${currentPlayer.deck.inHand.size()==0}">
                    <div class="col-sm-2">
                        <c:out value="There are no cards in hand"/>
                    </div>
                </c:if>
            </div>
        </div>
    </c:if>
    <hr>
    <div class="row">
        <h2>Chatea</h2>
        <div class="chatGroup"></div>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </c:if>
        <c:if test="${loggedPlayer.isNew()}">
            <a href="/turns">Reload</a>
        </c:if>
    </div>
    <div class="nextTurn"></div>
    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/currentTurn.js" type="module"></script>
    <script src="/resources/js/radioButtom.js" type="module"></script>
</nt4h:layout>
