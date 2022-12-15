<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Reestablishment Phase">
<body class="background">
<h2>Reestablishment Phase</h2>
<h1>Player´s turn: ${game.currentPlayer}</h1>

<form:form modelAttribute="enemiesInBattle" class="form-horizontal" id="choose-enemy-form">
    <div class="container">
        <c:forEach items="${game.actualOrcs}" var="i">
            <img class="card-img-top" src="${i.getEnemy().getFrontImage()}">
        </c:forEach>
    </div>
</form:form>

<form:form modelAttribute="player" class="form-horizontal" id="choose-player-form">
     <div class="container">
         <c:forEach items="${player.inHand}" var="i">
             <form:radiobutton path="currentAbility" value="${i}"/>
             <img class="card-img-top" src="${i.getAbility().getFrontImage()}">
         </c:forEach>
     </div>

     <button class="btn btn-default" type="submit">Discard Ability</button>

</form:form>

<a href="${pageContext.request.contextPath}/nextTurn" class="btn btn-default">Next Turn </a>

</nt4h:layout>



