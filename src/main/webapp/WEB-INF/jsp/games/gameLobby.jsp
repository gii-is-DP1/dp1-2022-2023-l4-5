<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="lobby">
    <h2>Game Lobby</h2>
    <div class="ready"></div>

    <div>
        <c:if test="${loggedPlayer.isNew()}">
            <h1>You are watching the game!</h1>
        </c:if>
        <c:if test="${loggedPlayer.ready}">
            <h1>You are ready!</h1>
        </c:if>
        <c:if test="${!loggedPlayer.ready && !loggedPlayer.isNew()}">
            <a href="/games/heroSelect">Add hero!</a>
        </c:if>
    </div>
    <h2>"Chat"</h2>
    <div class="next"></div>
    <div class="row">
        <div class="chatGroup"></div>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </c:if>
    </div>
    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/playersInLobby.js" type="module"></script>
</nt4h:layout>
