<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="hero selection">
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
    </style>
    <h2>Hero Selection</h2>
    <form:form modelAttribute="newHero" class="form-horizontal" id="choose-hero-form">
        <div class="container">
            <div class="pointer">
            <c:forEach var="i" begin="0" end="${heroes.size()-1}">
                <c:set var="hero" value="${heroes[i]}" scope="page"/>
                <div class="col-sm-2">
                    <nt4h:radioButtom name="hero" element="${hero}" frontImage="${hero.frontImage}" i="${i}0" image="/resources/images/muszka.png"/>
                </div>
            </c:forEach>
            </div>
        </div>
        <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
        <button class="btn btn-default" type="submit">Choose Hero</button>
    </form:form>
    <form method="post" action="#">
        <label for="option1">Opción 1:</label>
        <input type="checkbox" id="option1" name="options" value="opcion1" />
        <br>
        <label for="option2">Opción 2:</label>
        <input type="checkbox" id="option2" name="options" value="opcion2" />
        <br>
        <label for="option3">Opción 3:</label>
        <input type="checkbox" id="option3" name="options" value="opcion3" />
        <br>
        <label for="option4">Opción 4:</label>
        <input type="checkbox" id="option4" name="options" value="opcion4" />
        <br><br>
        <input type="submit" value="Enviar">
    </form>

    <script src="/resources/js/radioButtom.js" type="module">
</nt4h:layout>

