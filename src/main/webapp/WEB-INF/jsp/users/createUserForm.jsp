<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="CreateUsers">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${user['new']}">New </c:if> User
        </h2>

        <form:form modelAttribute="user" class="form-horizontal" id="add-user-form">
            <div class="form-group has-feedback">

                <petclinic:inputField label="Username" name="username"/>
                <petclinic:inputField label="Password" name="password"/>
                <petclinic:inputField label="Avatar" name="avatar"/>
                <petclinic:inputField label="Birth Date" name="birthDate"/>
                <petclinic:inputField label="Description" name="description"/>

            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${user['new']}">
                            <button class="btn btn-default" type="submit">Add User</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update User</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
