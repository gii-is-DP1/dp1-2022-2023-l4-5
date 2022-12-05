<%@ page import="org.springframework.samples.nt4h.user.User" %>
<%@ page import="org.springframework.samples.nt4h.game.Game" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="admins">
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
            <th style="width: 200px">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <spring:url value="/users/{userId}" var="userUrl">
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
                    <img src="${user.avatar}" alt="No image found" height="50rem" width="50rem"/>
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
                    <spring:url value="/friends/remove/{userId}" var="removeFriend">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(removeFriend)}" class="btn">Remove</a>
                    <spring:url value="/messages/{username}" var="chatWith">
                        <spring:param name="username" value="${user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(chatWith)}" class="btn">Chat</a>
                    <!--
                    <c:if test="${game != null}">
                        <spring:url value="/games/{gameId}" var="game">
                            <spring:param name="gameId" value="${game.id}"/>
                        </spring:url>
                        <a href="${fn:escapeXml(game)}" class="btn">Join</a>
                    </c:if>
                    -->

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</nt4h:layout>
