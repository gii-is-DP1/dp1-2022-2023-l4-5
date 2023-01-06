<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="image" required="true" rtexprvalue="true" description="Image path" %>
<%@attribute name="i" required="true" rtexprvalue="true" %>
<%@attribute name="element" required="true" rtexprvalue="true"%>
<%@attribute name="frontImage" required="true" rtexprvalue="true"%>
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
<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <div class="${cssGroup}">
        <div class="container">
        <form:radiobutton path="${name}" id="${i}r" value="${element}" class="custom-radio" alt="${frontImage}"/>
        <label for="${i}r" style="cursor: pointer;">
            <div id="${i}d">
                <img src="${frontImage}" class="card-img-top" id="${i}i" alt="${image}">
            </div>
        </label>
        </div>
    </div>
</spring:bind>

