<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="achievements">
    <h2>Achievements</h2>

    <table id="achievements" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Description</th>
            <th style="width: 200px;">Image</th>
            <th style="width: 200px">Threshold</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
                <td>
                    <spring:url value="/achievements/{achievementId}" var="achievementUrl">
                        <spring:param name="achievementId" value="${achievement.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(achievementUrl)}"><c:out value="${achievement.name}"/></a>
                </td>
                <td>
                    <c:out value="${achievement.description}"/>
                </td>
                <td>
                    <c:out value="${achievement.image}"/>
                </td>
                <td>
                    <c:out value="${achievement.threshold}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</nt4h:layout>
