<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="users">

    <h2>User Information</h2>


    <table class="table table-striped">
        <tr>
            <th>UserName</th>
            <td><b><c:out value="${loggedUser.username}" /></b></td>
        </tr>
        <tr>
            <th>Avatar</th>
            <td><img src="${loggedUser.avatar}" alt="No image found"/></td>
        </tr>
        <tr>
            <th>Tier</th>
            <td><c:out value="${loggedUser.tier}"/></td>
        </tr>
        <tr>
            <th>BirthDate</th>
            <td><c:out value="${loggedUser.birthDate}"/></td>
        </tr>
        <tr>
            <th>Authority</th>
            <td><c:out value="${loggedUser.authority}"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${loggedUser.description}"/></td>
        </tr>
    </table>

    <spring:url value="/users/edit" var="editUrl">

    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit User</a>
    <spring:url value="/" var="goToLobby">
    </spring:url>
    <a href="${fn:escapeXml(goToLobby)}" class="btn btn-default">Go to Menu</a>
    <spring:url value="/statistics" var="Statistics">

    </spring:url>
    <a href="${fn:escapeXml(Statistics)}" class="btn btn-default">Go to Statistics</a>
</nt4h:layout>
