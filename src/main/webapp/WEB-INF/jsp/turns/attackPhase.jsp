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
    </style>
    <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
        <h1>Gold: ${player.statistic.glory},
            Glory: ${player.statistic.gold},
            Wounds: ${player.wounds}</h1>
        <div class="container">
            <c:forEach items="${player.deck.inHand}" var="i">
                <div class="col-sm-2">
                    <form:radiobutton path="currentAbility" value="${i}"/>
                    <img class="card-img-top" src="${i.ability.frontImage}" alt="Card image cap">
                </div>
            </c:forEach>
        </div>
        <div class="container">
            <c:forEach items="${game.actualOrcs}" var="i">
                <div class="col-sm-2">
                    <form:radiobutton path="currentEnemy" value="${i}"/>
                    <img class="card-img-top" src="${i.getEnemy().getFrontImage()}">
                </div>
            </c:forEach>
        </div>

        <c:if test="${game.actualOrcs.size()!=0}">
            <button class="btn btn-default" type="submit">Attack</button>
        </c:if>

    </form:form>
    <div class="nextTurn"></div>
    <script src="/resources/js/currentTurn.js" type="module"></script>
    <script type="text/javascript">
        if (${game.currentTurn.phase.toString()=='enemyAttack'})
            alert("The orcs makes you " + ${damage} +".");
    </script>

</nt4h:layout>
