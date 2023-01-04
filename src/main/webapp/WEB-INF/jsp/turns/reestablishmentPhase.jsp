<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Reestablishment Phase">
<body class="background">
<h1>Reestablishment Phase</h1>
<h2>${game.currentPlayer}`s Turn</h2>

<div class="container">
    <c:forEach items="${game.actualOrcs}" var="i">
        <img class="card-img-top" src="${i.enemy.frontImage}">
    </c:forEach>
</div>

<form:form modelAttribute="turn" class="form-horizontal" id="choose-player-form">
     <div class="container">
         <c:forEach items="${player.deck.inHand}" var="i">
             <form:radiobutton path="currentAbility" value="${i}"/>
             <img class="card-img-top" src="${i.ability.frontImage}">
         </c:forEach>
     </div>
     <button class="btn btn-default" type="submit">Discard Ability</button>
</form:form>

    <div class="nextTurn"></div>
    <script src="/resources/js/currentTurn.js" type="module"></script>

</nt4h:layout>

<style>
    .background {
        background-image: url("/resources/images/campo.jpg");
        background-repeat: no-repeat;
        background-size: cover;
    }
</style>


