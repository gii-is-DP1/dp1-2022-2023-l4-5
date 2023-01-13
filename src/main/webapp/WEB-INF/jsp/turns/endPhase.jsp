<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="nth4" uri="http://www.springframework.org/tags/form" %>

<nt4h:layout pageName="Action decision">
    <style>
        .pointer {
            display: flex;
            justify-content: center;
        }
    </style>
    <h2>Action decision</h2>
    <h1>Turno del jugador ${currentPlayer}</h1>
    <ol>
    <c:forEach var="puntuation" items="${punctuations}">
        <li>${puntuation.value0}: ${puntuation.value1}</li>
    </c:forEach>
    </ol>
    <hr>
    <div>
        <h2>Chatea</h2>
        <div class="chatGroup"></div>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </c:if>
    </div>
    <a href="/end/finish" class="btn btn-danger">Leave</a>
    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/radioButtom.js" type="module"></script>
</nt4h:layout>
