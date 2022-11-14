<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="selectOrder">

    <c:forEach var="players" items="${game.players}">
        <tr>
            <td>
                <c:out value="${players.name}"/>
            </td>
            <td>
                Turno que te toca pelotudo:
                <c:out value="${players.sequence}"/>
            </td>
            <td>
                <c:out value="${players.inDeck.get(0).getName()} + ${players.inDeck.get(1).getName()} = ${players.inDeck.get(0).getAttack()+players.inDeck.get(1).getAttack()}"/>
            </td>

        </tr>

    </c:forEach>


</nt4h:layout>
