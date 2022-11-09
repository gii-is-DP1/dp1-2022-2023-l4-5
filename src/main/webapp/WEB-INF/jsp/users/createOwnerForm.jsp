<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="CreateUsers">
    <jsp:body>
        <h2>
            <c:if test="${users['new']}">New </c:if> User
        </h2>

        <form:form modelAttribute="user" class="form-horizontal" id="add-user-form">
            <div class="form-group has-feedback">
                UserName:
                <input label="UserName" name="username"/>
                Description
                <input label="description" name="description"/>
                Password:
                <input type="password" label="Password" name="password"/>
                BirthDate:
                <input type="date" label="BirthDate" name="BirthDate"/>
                Avatar:
                <input type="url" label="Avatar" name="avatar"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Add User</button>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
