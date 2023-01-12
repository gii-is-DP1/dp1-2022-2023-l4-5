<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>


<nt4h:layout pageName="games">
    <h2>Games</h2>
    <table id="games" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Start Date</th>
            <th style="width: 200px;">Players</th>
            <th style="width: 200px">Mode</th>
            <th style="width: 200px;">Accessibility</th>
            <th style="width: 200px;">Unirse</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <c:if test="${game.getPlayers().size() != 0}">
            <tr>
                <td>
                    <c:out value="${game.name}"/>
                </td>
                <td>
                    <c:out value="${game.startDate}"/>
                </td>
                <td>
                    <c:out value="${fn:length(game.players)}/${game.maxPlayers}"/>
                </td>
                <td>
                    <c:out value="${game.mode}"/>
                </td>
                <td>
                    <c:out value="${game.accessibility}"/>
                </td>
                <td>
                    <a href="/games/view/${game.id}" class="btn btn-primary">
                        <c:if test="${game.spectators == null}">
                            <c:out value="0 View"/>
                        </c:if>
                        <c:if test="${game.spectators != null}">
                            <c:out value="${fn:length(game.spectators)}  View"/>
                        </c:if>
                    </a>
                    <c:choose>
                        <c:when test="${game.accessibility.isPublic()}">
                            <a class="btn btn-default" href='<spring:url value="/games/${game.id}" htmlEscape="true"/>'>Join
                                Game</a>
                        </c:when>
                        <c:otherwise>
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    data-target="#exampleModalCenter">
                                Join Game
                            </button>
                            <!-- Modal -->
                            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
                                 aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLongTitle">Introduce Password</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div style="text-align: center;">

                                                <input type="password" class="input">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                            </button>
                                            <a class="redirection" href="/games/${game.id}?password=">
                                                <button type="button" class="btn btn-primary">Join</button>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
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


