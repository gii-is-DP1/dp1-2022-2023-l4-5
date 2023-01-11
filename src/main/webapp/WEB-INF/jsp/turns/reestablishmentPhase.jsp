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
    <h2>${currentPlayer}`s Turn</h2>
    <div class="container">
        <c:forEach items="${game.actualOrcs}" var="orc">
            <img src="${orc.enemy.frontImage}"> <!-- TODO: Reducir tamaño -->
        </c:forEach>
    </div>
    <c:if test="${!loggedPlayer.isNew()}">
        <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
            <div class="container">
                <c:if test="${currentPlayer.deck.inHand.size() != 0}">
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
                    <div class="display: flex; justify-content: center;">
                        <c:out value="You have no abilities in your hand."/>
                    </div>
                </c:if>
            </div>
            <button class="btn btn-default" type="submit">Discard Ability</button>
        </form:form>
    </c:if>
    <c:if test="${loggedPlayer.isNew()}">
        <div class="container">
            <c:if test="${currentPlayer.deck.inHand.size()!=0}">
                <div class="display: flex; justify-content: center;">
                    <c:forEach var="i" begin="0" end="${currentPlayer.deck.inHand.size()-1}">
                        <c:set var="abilityInGame" value="${currentPlayer.deck.inHand[i]}" scope="page"/>
                        <div class="col-sm-2">
                            <img src="${abilityInGame.ability.frontImage}">
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${loggedPlayer.player.deck.inHand.size()==0}">
                <div class="display: flex; justify-content: center;">
                    <c:out value="No abilities in hand"/>
                </div>
            </c:if>
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
    <script src="/resources/js/radioButtom.js" type="module">
</nt4h:layout>
