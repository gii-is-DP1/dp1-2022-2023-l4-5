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
    <h1>Turno del jugador ${game.currentPlayer}</h1>
    <c:set var="evasion" value="/resources/images/rajoy.png"/>
    <c:set var="attack" value="${evasion.action}"/>
    <c:if test="${!loggedPlayer.isNew()}">
        <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
            <div class="pointer">
                <div class="col-sm-2">
                    <nt4h:radioButtom name="phase" element="${turns[0]}" frontImage="/resources/images/rajoy.png" i="00" image="/resources/images/muszka.png"/>
                </div>
                <div class="col-sm-2">
                    <nt4h:radioButtom name="phase" element="${turns[1]}" frontImage="/resources/images/espada.png" i="10" image="/resources/images/muszka.png"/>
                </div>
            </div>
            <button class="btn btn-default" type="submit">Action chosen</button>
        </form:form>
    </c:if>
    <c:if test="${loggedPlayer.isNew()}">
        <div style="display: flex; justify-content: center;">
            <div class="col-sm-2">
                <img src="${evasion}">
            </div>
            <div class="col-sm-2">
                <img src="${attack}">
            </div>
        </div>
    </c:if>


    <div>
        <div class="chatGroup"></div>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </c:if>
    </div>
    <div class="nextTurn"></div>
    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/radioButtom.js" type="module"></script>
    <script src="/resources/js/currentTurn.js" type="module"></script>
</nt4h:layout>
