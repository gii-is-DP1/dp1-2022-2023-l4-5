<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>


<nt4h:layout pageName="games">
    <h2>Games</h2>
    <table id="games" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Start Date</th>
            <th style="width: 200px;">Players</th>
            <th style="width: 200px">Mode</th>
            <th style="width: 200px;">Accessibility</th>
            <th style="width: 200px;">List players</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
                <td>
                    <c:out value="${game.name}"/>
                    <a href="/delete/game/${game.id}">Delete game</a>
                </td>
                <td>
                    <c:out value="${game.startDate}"/>
                </td>
                <td>
                    <c:out value="${fn:length(game.players)}/${game.maxPlayers}"/>
                </td>
                <td>
                    <c:out value="${game.mode}"/>
                </td>
                <td>
                    <c:out value="${game.accessibility}"/>
                </td>
                <td>
                    <c:for value="player" elements="${game.players}">
                        <ul>
                            <li>
                                <c:out value="${player.name} | ${player.heroes.stream().map(heroInGAme -> heroInGame.hero.name).collect(Collectors.joining(","))}"/>
                                <a href="/games/deletePlayer/${player.id}">Delete player!!</a>
                            </li>
                        </ul>
                    </c:for>
                </td>
        </tbody>
    </table>
    <c:if test="${page > 0}">
        <spring:url value="/admins/games?page={previous}" var="previous">
            <spring:param name="previous" value="${page-1}"/>
        </spring:url>
        <a href="${fn:escapeXml(previous)}" class="btn">Previous</a>
    </c:if>
    <c:if test="${isNext}">
        <spring:url value="/admins/games?page={next}" var="next">
            <spring:param name="next" value="${page+1}"/>
        </spring:url>
        <a href="${fn:escapeXml(next)}" class="btn">Next</a>
    </c:if>
    <script src="/resources/js/changeVariable.js" type="text/javascript"></script>
</nt4h:layout>
