<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>



<nt4h:layout pageName="games">
    <h2>Games</h2>
    <c:out value="${message}"/>
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
                <c:choose>
                    <c:when test="game.Accessibility.isPublic">
                        <a class="btn btn-default" href='<spring:url value="/games/${game.id}" htmlEscape="true"/>'>Join Game</a>
                    </c:when>
                    <c:otherwise>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
                            Join Game
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
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
                                            <input type="password" id="input">
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                        <a id="redirection" href="/games/${game.id}?password=">
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
        </c:forEach>
        </tbody>
    </table>
</nt4h:layout>

<script type="text/javascript">
    const input = document.getElementById("input");
    console.log("hola " + input.value);
    input.addEventListener("keyup", function (event) {
        const bottom = document.getElementById("redirection");
        const url = bottom.getAttribute("href");
        bottom.setAttribute("href", url.replace(/=.*/, "=" + input.value));
        console.log(bottom.getAttribute("href"));
    });
</script>
