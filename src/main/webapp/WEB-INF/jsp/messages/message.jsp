<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->

<petclinic:layout pageName="chat">
    <h2>"Chatea con ${chat.receiver.username}"</h2>
    <div class="row">
        <c:forEach items="${messages}" var="m">
            <li>${m.sender.username}: ${m.content}</li>
        </c:forEach>
        <form:form modelAttribute="chat" class="form-horizontal">
            <petclinic:inputField label="Content" name="content"/>
            <button class="btn btn-default" type="submit">Send</button>
        </form:form>

    </div>
</petclinic:layout>
