<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="capacities">
    <h2>Capacities</h2>

    <table id="capacitiesTable" class="table table-striped">
        <thead>
        <tr>
            <th>State capacity</th>
            <th>Less damage</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${capacities}" var="capacity">
            <tr>
                <td>
                    <c:out value="${capacity.stateCapacity}"/>
                </td>
                <td>
                    <c:if test="${capacity.lessDamage}">Yes</c:if>
                    <c:if test="${!capacity.lessDamage}">No</c:if>
                </td>
                <td>
                    <a href="/capacities/${capacity.id}/edit">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    </a>
                </td>
                <td>
                    <a href="/capacities/${capacity.id}/delete">
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href="/capacities/new">Create new capacity</a>
</petclinic:layout>
