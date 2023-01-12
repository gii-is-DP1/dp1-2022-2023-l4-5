<%@ page import="org.springframework.samples.nt4h.user.User" %>
<%@ page import="org.springframework.samples.nt4h.game.Game" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nt4h:layout pageName="users">
    <h2>Users</h2>

    <table id="users" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Username</th>
            <sec:authorize access="hasAuthority('DOKTOL')">
                <th style="width: 200px;">Password</th>
                <th style="width: 200px;">Enable</th>
                <th style="width: 200px;">Authority</th>
            </sec:authorize>

            <th style="width: 200px">Avatar</th>
            <th style="width: 200px;">Tier</th>
            <th style="width: 200px;">Description</th>

            <th style="width: 200px;">BirthDate</th>
            <th style="width: 200px">Actions</th>
            <sec:authorize access="hasAuthority('DOKTOL')">
                <th style="width: 200px;">ADMIN Actions</th>
            </sec:authorize>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="localUser">
            <tr>
                <td>
                    <c:out value="${localUser.username}"/>
                </td>
                <sec:authorize access="hasAuthority('DOKTOL')">
                    <td>
                        <c:out value="${localUser.password}"/>
                    </td>
                    <td>
                        <c:out value="${localUser.enable}"/>
                    </td>
                    <td>
                        <c:out value="${localUser.authority}"/>
                    </td>
                </sec:authorize>
                <td>
                    <img src="${localUser.avatar}" alt="No image found" height="50rem" width="50rem"/>
                </td>
                <td>
                    <c:out value="${localUser.tier}"/>
                </td>
                <td>
                    <c:out value="${localUser.description}"/>
                </td>

                <td>
                    <c:out value="${localUser.birthDate}"/>
                </td>
                <td>
                    <c:if test="${localUser.id != loggedUser.id}">
                        <c:choose>
                            <c:when test="${loggedUser.getFriends().contains(localUser)}">
                                <spring:url value="/friends/remove/{userId}" var="removeFriend">
                                    <spring:param name="userId" value="${localUser.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(removeFriend)}" class="btn">Remove </a>
                                <c:if test="${localUser.game != null}">
                                    <spring:url value="/games/{gameId}" var="game">
                                        <spring:param name="gameId" value="${game.id}"/>
                                    </spring:url>
                                    <a href="${fn:escapeXml(game)}" class="btn">Join</a>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <spring:url value="/friends/add/{userId}" var="addFriend">
                                    <spring:param name="userId" value="${localUser.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(addFriend)}" class="btn">Add Friend</a>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </td>

                <sec:authorize access="hasAuthority('DOKTOL')">
                    <td>
                        <c:if test="${localUser.id != loggedUser.id}">
                            <spring:url value="/admins/{userId}/delete" var="deleteUser">
                                <spring:param name="userId" value="${localUser.id}"/>
                            </spring:url>
                            <a href="${fn:escapeXml(deleteUser)}" class="btn">Delete</a>
                        </c:if>
                        <spring:url value="/admins/{userId}/edit" var="editUser">
                            <spring:param name="userId" value="${localUser.id}"/>
                        </spring:url>
                        <a href="${fn:escapeXml(editUser)}" class="btn">Edit</a>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${page > 0}">
        <spring:url value="/users?page={previous}" var="previous">
            <spring:param name="previous" value="${page-1}"/>
        </spring:url>
        <a href="${fn:escapeXml(previous)}" class="btn">Previous</a>
    </c:if>
    <c:if test="${isNext}">
        <spring:url value="/users?page={next}" var="next">
            <spring:param name="next" value="${page+1}"/>
        </spring:url>
        <a href="${fn:escapeXml(next)}" class="btn">Next</a>
    </c:if>


</nt4h:layout>
