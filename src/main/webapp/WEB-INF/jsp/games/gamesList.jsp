<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Games</h2>

    <table id="games" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Start Date</th>
            <th style="width: 200px;">Max Players</th>
            <th style="width: 200px">Mode</th>
            <th style="width: 200px;">Accessibility</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="game">
            <tr>
                <td>
                    <c:out value="${game.startDate}"/>
                </td>
                <td>
                    <c:out value="${game.maxPlayers}"/>
                </td>
                <td>
                    <c:out value="${game.mode}"/>
                </td>
                <td>
                    <c:out value="${game.accessibility}"/>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
