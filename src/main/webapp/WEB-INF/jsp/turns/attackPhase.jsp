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
    <c:if test="${!loggedPlayer.isNew()}">
        <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
            <div class="container">
                <c:if test="${game.actualOrcs.size()!=0}">
                    <div class="pointer">
                        <c:forEach var="i" begin="0" end="${game.actualOrcs.size()-1}">
                            <c:set var="enemyInGame" value="${game.actualOrcs[i]}" scope="page"/>
                            <div class="row">
                                <div class="col-8">
                                    <p style="font-size:2rem">
                                        <img src="/resources/images/heart_orc.gif" width="50"
                                             height="50"> ${enemyInGame.actualHealth}
                                    </p>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <nt4h:radioButtom name="currentEnemy" element="${enemyInGame.id}"
                                                  frontImage="${enemyInGame.enemy.frontImage}" i="${i}0"
                                                  image="/resources/images/muszka.png"/>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${game.actualOrcs.size()==0}">
                    <div class="display: flex; justify-content: center;">
                        <c:out value="There are no enemies in the battle."/>
                    </div>
                </c:if>
            </div>
            <div class="container">
                <c:if test="${currentPlayer.deck.inHand.size() != 0}">
                    <div class="pointer">
                        <c:forEach var="i" begin="0" end="${currentPlayer.deck.inHand.size()-1}">
                            <c:set var="abilityInGame" value="${currentPlayer.deck.inHand[i]}" scope="page"/>

                            <div class="col-sm-2">
                                <nt4h:radioButtom name="currentAbility" element="${abilityInGame.id}"
                                                  frontImage="${abilityInGame.ability.frontImage}" i="${i}1"
                                                  image="/resources/images/muszka.png"/>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${currentPlayer.deck.inHand.size()==0}">
                    <div class="display: flex; justify-content: center;">
                        <c:out value="You have no abilities in your hand."/>
                    </div>
                </c:if>
            </div>
            <c:if test="${game.actualOrcs.size()!=0 && currentPlayer.deck.inHand.size()!=0 && game.currentTurn.phase.toString()!='enemyAttack'}">
                <button class="btn btn-default" type="submit">Attack</button>
            </c:if>
        </form:form>
    </c:if>
    <c:if test="${logggedPlayer.isNew() }">
        <div class="container">
            <div class="display: flex; justify-content: center;">
                <c:if test="${game.actualOrcs.size()!=0}">
                    <c:forEach var="i" begin="0" end="${game.actualOrcs.size()-1}">
                        <c:set var="enemyInGame" value="${game.actualOrcs[i]}" scope="page"/>
                        <div class="row">
                            <div class="col-8">
                                <p style="font-size:2rem">
                                    <img src="/resources/images/heart_orc.gif" width="50"
                                         height="50"> ${enemyInGame.actualHealth}
                                </p>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <img src="${enemyInGame.enemy.frontImage}">
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${game.actualOrcs.size()==0}">
                    <div class="display: flex; justify-content: center;">
                        <c:out value="There are no enemies in the battle."/>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="container">
            <div style="display: flex; justify-content: center;">
                <c:if test="${currentPlayer.deck.inHand.size()!=0}">
                    <c:forEach var="i" begin="0" end="${currentPlayer.deck.inHand.size()-1}">
                        <c:set var="abilityInGame" value="${currentPlayer.deck.inHand[i]}" scope="page"/>
                        <div class="col-sm-2">
                            <img src="${abilityInGame.ability.frontImage}">
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${currentPlayer.deck.inHand.size()==0}">
                    <div class="display: flex; justify-content: center;">
                        <c:out value="You have no abilities in your hand."/>
                    </div>
                </c:if>
            </div>
        </div>
    </c:if>
    <hr>
    <div class="row">
        <h2>Chatea</h2>
        <div class="chatGroup"></div>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </c:if>
    </div>
    <div class="nextTurn"></div>
    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/currentTurn.js" type="module"></script>
    <script type="text/javascript">
        if (${game.currentTurn.phase.toString()=='enemyAttack'})
            alert("The orcs makes you " + ${damage} +".");
    </script>
    <script src="/resources/js/radioButtom.js" type="module"></script>

</nt4h:layout>
