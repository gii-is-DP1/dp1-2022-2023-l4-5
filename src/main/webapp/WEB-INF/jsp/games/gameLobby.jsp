<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="lobby">
    <h2>Game Lobby</h2>
    <c:forEach items="${selections}" var="player">
        <h1>"${player.name}"</h1>

        <form:form action="submitForm" modelAttribute="p">
            <div class="form-group">
                <petclinic:selectField name="Hero" label="Hero" names="${[A,B,C]}" size="2"/>
            </div>
            <input type="submit"  value="Ready" />
        </form:form>

    </c:forEach>
</petclinic:layout>
