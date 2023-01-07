<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="selectOrder">
    <jsp:body>
        <h2>
            <c:if test="${game['new']}">New </c:if> Game
        </h2>

        <form:form modelAttribute="game" class="form-horizontal" >
            <button id="waiting" hidden="hidden" type="submit">Start game</button>
        </form:form>
        <div class="row">
            <div class="chatGroup"></div>
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </div>
        <script src="/resources/js/chatGroup.js" type="module"></script>
        <script src="/resources/js/waitUntilAllReady.js" type="module"></script>
    </jsp:body>

</nt4h:layout>





