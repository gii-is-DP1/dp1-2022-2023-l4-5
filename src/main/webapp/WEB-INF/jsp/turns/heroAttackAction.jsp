<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Hero Attack Action">

<form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
    <nt4h:selectField name="usedAbilities" label="abilities in hand" names="${player.inHand}" size="4"/>
    <nt4h:selectField name="usedEnemies" label="enemies in battle" names="${game.actualOrcs}" size="3"/>
    <button class="btn btn-default" type="submit">Attack</button>
</form:form>

<a href="${pageContext.request.contextPath}/heroAttack/next" class="btn btn-default">Next phase</a>

</nt4h:layout>
