<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nt4h:layout pageName="achievements">
    <h2>Achievements</h2>

    <table id="achievements" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Description</th>
            <th style="width: 200px;">Image</th>
            <th style="width: 200px">Threshold</th>
            <sec:authorize access="hasAuthority('DOKTOL')">
                <th style="width: 200px">Action</th>
            </sec:authorize>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
                <td>
                    <c:out value="${achievement.name}"/>
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
                <sec:authorize access="hasAuthority('DOKTOL')">
                <td>

                    <spring:url value="/achievements/{achievementId}/edit" var="editAchievement">
                    <spring:param name="achievementId" value="${achievement.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editAchievement)}" class="btn">Edit</a>

                    <spring:url value="/achievements/{achievementId}/delete}" var="deleteAchievement">
                    <spring:param name="achievementId" value="${achievement.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteAchievement)}" class="btn">Delete</a>

                </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</nt4h:layout>
