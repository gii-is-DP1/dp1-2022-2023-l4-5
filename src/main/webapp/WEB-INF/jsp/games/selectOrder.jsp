<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="selectOrder">

    <table id="games" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Player</th>
            <th style="width: 200px;">Order</th>
            <th style="width: 200px">Reasons</th>
        </tr>
        </thead>
        <c:forEach var="player" items="${game.players}">
            <tbody>
            <tr>
                <td>
                    <c:out value="${player.name}"/>
                </td>
                <td>
                    <c:out value="${player.sequence + 1}"/>
                </td>
                <td>
                    <c:out value="${player.birthDate.year} - ${player.birthDate.month.value} - ${player.birthDate.dayOfMonth} | ${player.inDeck.get(0).ability.name} + ${player.inDeck.get(1).ability.name} = ${player.inDeck.get(0).ability.attack+player.inDeck.get(1).ability.attack}"/>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
    <a href="/turns">
        <button type="button" class="btn btn-primary">Next</button>
    </a>
</nt4h:layout>
