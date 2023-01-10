<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="statistics">

    <h2>${loggedUser}`s Statistics</h2>
    <table class="table table-striped">
        <tr>
            <th>Total Played Games</th>
            <td><b><c:out value="${statistic.numPlayedGames}" /></b></td>
        </tr>
        <tr>
            <th>Total Won Games</th>
            <td><b><c:out value="${statistic.numWonGames}" /></b></td>
        </tr>
        <tr>
            <th>Total Played Time</th>
            <td><b><c:out value="${statistic.timePlayed}" /></b></td>
        </tr>
        <tr>
            <th>Total Obtained Gold</th>
            <td><b><c:out value="${statistic.gold}" /></b></td>
        </tr>
        <tr>
            <th>Total Obtained Glory</th>
            <td><b><c:out value="${statistic.glory}" /></b></td>
        </tr>
        <tr>
            <th>Total Dealt Damage To Orcs</th>
            <td><b><c:out value="${statistic.dealtDamage}" /></b></td>
        </tr>
        <tr>
            <th>Total Amount Of Players In Games</th>
            <td><b><c:out value="${statistic.numPlayers}" /></b></td>
        </tr>
        <tr>
            <th>Total Killed Orcs</th>
            <td><b><c:out value="${statistic.numOrcsKilled}" /></b></td>
        </tr>
        <tr>
            <th>Total Killed WarLords</th>
            <td><b><c:out value="${statistic.numWarLordKilled}" /></b></td>
        </tr>
    </table>

    <spring:url value="/" var="goToLobby"></spring:url>
    <a href="${fn:escapeXml(goToLobby)}" class="btn btn-default">Go to Menu</a>

    <spring:url value="/statistics/allStatistics" var="goToAllStatistics"></spring:url>
    <a href="${fn:escapeXml(goToAllStatistics)}" class="btn btn-default">All Statistics</a>

</nt4h:layout>
