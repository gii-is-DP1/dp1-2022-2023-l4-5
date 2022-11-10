<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">

    <h2>User Information</h2>


    <table class="table table-striped">
        <tr>
            <th>UserName</th>
            <td><b><c:out value="${user.username}" /></b></td>
        </tr>
        <tr>
            <th>Avatar</th>
            <td><c:out value="${user.avatar}"/></td>
        </tr>
        <tr>
            <th>Tier</th>
            <td><c:out value="${user.tier}"/></td>
        </tr>
        <tr>
            <th>BirthDate</th>
            <td><c:out value="${user.birthDate}"/></td>
        </tr>
        <tr>
            <th>Authority</th>
            <td><c:out value="${user.authority}"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${user.description}"/></td>
        </tr>
    </table>

    <spring:url value="{userId}/edit" var="editUrl">
        <spring:param name="userId" value="${user.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit User</a>

    <spring:url value="/welcome" var="goToLobby">
    </spring:url>
    <a href="${fn:escapeXml(goToLobby)}" class="btn btn-default">Go to Lobby</a>

</petclinic:layout>
