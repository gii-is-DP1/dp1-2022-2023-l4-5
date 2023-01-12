<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>


<nt4h:layout pageName="finished">
    <h2>Games</h2>
    <table id="games" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Start Date</th>
            <th stye="width: 200px;"> Finish Date</th>
            <th style="width: 200px;">Host</th>
            <th style="width: 200px;">Players</th>
            <th style="width: 200px">Mode</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <c:if test="${game.finishDate != null}">
                <tr>
                    <td>
                        <c:out value="${game.name}"/>
                    </td>
                    <td>
                        <c:out value="${game.startDate}"/>
                    </td>
                    <td>
                        <c:out value="${game.finishDate}"/>
                    </td>
                    <td>
                        <c:forEach items="${game.players}" var="player">
                        <c:if test="${player.host == true}">
                            <c:out value="${player}"/>
                        </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <ul>
                        <c:forEach items="${game.players}" var="player">
                            <c:if test="${player.host == false}">
                                <li><c:out value="${player}"/></li>
                            </c:if>
                        </c:forEach>
                                </ul>
                    </td>
                    <td>
                        <c:out value="${game.mode}"/>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${page > 0}">
        <spring:url value="/games?page={previous}" var="previous">
            <spring:param name="previous" value="${page-1}"/>
        </spring:url>
        <a href="${fn:escapeXml(previous)}" class="btn">Previous</a>
    </c:if>
    <c:if test="${isNext}">
        <spring:url value="/games?page={next}" var="next">
            <spring:param name="next" value="${page+1}"/>
        </spring:url>
        <a href="${fn:escapeXml(next)}" class="btn">Next</a>
    </c:if>
    <script src="/resources/js/changeVariable.js" type="text/javascript"></script>
</nt4h:layout>
