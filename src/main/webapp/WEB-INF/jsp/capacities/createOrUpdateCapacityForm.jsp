<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="capacities">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${achievement['new']}">New </c:if> Pet
        </h2>
        <form:form modelAttribute="capacity"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${capacity.id}"/>
            <div class="form-group has-feedback">
                <petclinic:inputField label="State capacity" name="stateCapacity"/>
                <petclinic:inputField label="Less damage" name="lessDamage"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${capacity['new']}">
                            <button class="btn btn-default" type="submit">Add Capacity</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Capacity</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
