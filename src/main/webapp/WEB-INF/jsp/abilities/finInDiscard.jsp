<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Hero Attack Action">
    <h1>${game.currentPlayer}`s Turn</h1>
    <style>
        .card-img-top {
            width: 100%;
            padding-top: 2rem;
            padding-bottom: 2rem;
        }

        .pointer {
            display: flex;
            justify-content: center;
        }
    </style>
    <div class="container">
        <c:forEach var="i" begin="0" end="${currentPlayer.health}">
            <img src="/resources/images/heart_hero.gif" width="50" height="50">
        </c:forEach>
        <p style="font-size:5rem">
            <img src="/resources/images/gloria.gif" width="50" height="50"> ${currentPlayer.statistic.glory}
            <img src="/resources/images/coin.gif" width="50" height="50"> ${currentPlayer.statistic.gold}
        </p>
    </div>
    <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
        <div class="container">
            <c:if test="${discard.size() != 0}">
                <div class="pointer">
                    <c:forEach var="i" begin="0" end="${discard.size()-1}">
                        <c:set var="abilityInGame" value="${discard[i]}" scope="page"/>
                        <div class="col-sm-2">
                            <nt4h:radioButtom name="currentAbility" element="${abilityInGame.id}"
                                              frontImage="${abilityInGame.ability.frontImage}" i="${i}1"
                                              image="/resources/images/muszka.png"/>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${discard.size()==0}">
                <div class="display: flex; justify-content: center;">
                    <c:out value="You have no abilities in your hand."/>
                </div>
            </c:if>
        </div>
        <c:if test="${discard.size()!=0 }">
            <button class="btn btn-default" type="submit">Choose card</button>
        </c:if>
    </form:form>
    <hr>
    <div class="row">
        <h2>Chatea</h2>
        <div class="chatGroup"></div>
        <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
            <nt4h:inputField label="Content" name="content"/>
        </form:form>
    </div>
    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/radioButtom.js" type="module"></script>
</nt4h:layout>
