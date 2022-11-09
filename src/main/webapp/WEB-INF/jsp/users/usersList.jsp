<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>Users</h2>

    <table id="users" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Username</th>
            <th style="width: 200px;">Password</th>
            <th style="width: 200px;">Enable</th>
            <th style="width: 200px">Avatar</th>
            <th style="width: 200px;">Tier</th>
            <th style="width: 200px;">Description</th>
            <th style="width: 200px;">Authority</th>
            <th style="width: 200px;">BirthDate</th>
            <th style="width: 200px">Friends</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="user">
            <tr>
                <td>
                    <spring:url value="/user/{userId}" var="userUrl">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(userUrl)}"><c:out value="${user.username}"/></a>
                </td>
                <td>
                    <c:out value="${user.password}"/>
                </td>
                <td>
                    <c:out value="${user.enable}"/>
                </td>
                <td>
                    <c:out value="${user.avatar}"/>
                </td>
                <td>
                    <c:out value="${user.tier}"/>
                </td>
                <td>
                <c:out value="${user.description}"/>
                </td>
                <td>
                    <c:out value="${user.authority}"/>
                </td>
                <td>
                    <c:out value="${user.birthDate}"/>
                </td>
                <td>
                    <c:forEach var="user" items="${user.friends}">
                        <c:out value="${user.username} "/>
                    </c:forEach>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
