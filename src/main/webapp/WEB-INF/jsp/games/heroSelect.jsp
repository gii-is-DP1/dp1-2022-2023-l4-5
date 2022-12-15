<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="hero selection">
    <style>
        .card-img-top {
            width: 100%;
            padding-top: 2rem;
            padding-bottom: 2rem;
        }
    </style>
    <h2>Hero Selection</h2>
    <form:form modelAttribute="hero" class="form-horizontal" id="choose-hero-form">

        <div class="container">
            <c:forEach items="${heroes}" var="i">
                <div class="col-sm-2">
                    <form:radiobutton path="hero" value="${i}"/>

                    <img class="card-img-top" src="${i.getFrontImage()}">
                </div>
            </c:forEach>
        </div>


        <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
        <button class="btn btn-default" type="submit">Choose Hero</button>
    </form:form>
</nt4h:layout>
