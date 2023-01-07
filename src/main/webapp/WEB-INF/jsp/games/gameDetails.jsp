<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="games">

    <h2>Game Information</h2>
    El código de abajo está puseto para ver si en añgún momento peta la aplicación.
    <spring:layout pageName=""
    <spring:url value="/welcome" var="goToLobby">
    </spring:url>
    <a href="${fn:escapeXml(goToLobby)}" class="btn btn-default">Go to Lobby</a>

</nt4h:layout>
