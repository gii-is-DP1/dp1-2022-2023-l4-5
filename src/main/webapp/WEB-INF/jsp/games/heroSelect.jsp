<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<style>
    .overlay {
        position: absolute;
        top: 5rem;
        left: 5rem;
        width: 50%;
        height: 50%;
    }

    .custom-radio {
        display: none;
    }

    .custom-radio + label {
        background-size: cover;
    }

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
<nt4h:layout pageName="hero selection">

    <h2>Hero Selection</h2>
    <form:form modelAttribute="newHero" class="form-horizontal" id="choose-hero-form">
        <div class="container">
            <div class="pointer">
                <c:if test="${!loggedPlayer.isNew()}">
                    <c:forEach var="i" begin="0" end="${heroes.size()-1}">
                        <c:set var="hero" value="${heroes[i]}" scope="page"/>
                        <div class="col-sm-2">
                            <nt4h:radioButtom name="hero" element="${hero}" frontImage="${hero.frontImage}" i="${i}0"
                                              image="/resources/images/muszka.png"/>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${loggedPlayer.isNew()}">
                    <c:forEach var="i" begin="0" end="${heroes.size()-1}">
                        <c:set var="hero" value="${heroes[i]}" scope="page"/>
                        <div class="col-sm-2">
                            <img src="${hero.frontImage}" style="cursor: pointer">
                        </div>
                    </c:forEach>
                </c:if>

            </div>
        </div>
        <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
        <button class="btn btn-default" type="submit">Choose Hero</button>
    </form:form>
    <div class="row">
        <div class="chatGroup"></div>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </c:if>
    </div>

    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/radioButtom.js" type="module"></script>
</nt4h:layout>

