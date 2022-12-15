<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Reestablishment Phase">
    <style>
        .card-img-top {
            width: 100%;
            padding-top: 2rem;
            padding-bottom: 2rem;
        }
    </style>
    <body class="background">
    <h2>Reestablishment Phase</h2>
    <h1>Player´s turn: ${game.currentPlayer}</h1>
    <div class="container">
        <c:forEach items="${game.actualOrcs}" var="orc">
            <div class="col-sm-2">
            <img class="card-img-top" src="${orc.enemy.frontImage}">
            </div>
        </c:forEach>
    </div>
    <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-enemy-form">
        <div class="container">
            <c:forEach items="${player.inHand}" var="abilityInGame">
                <div class="col-sm-2">
                    <form:radiobutton path="currentAbility" value="${abilityInGame}"/>
                    <img class="card-img-top" src="${abilityInGame.ability.frontImage}">
                </div>
            </c:forEach>
        </div>

        <button class="btn btn-default" type="submit">Discard Ability</button>
    </form:form>
    <a href="${pageContext.request.contextPath}/reestablishment/turns" class="btn btn-default">Next Turn</a>

</nt4h:layout>

<style>
    .background {
        background-image: url("/resources/images/campo.jpg");
        background-repeat: no-repeat;
        background-size: cover;
    }
</style>


