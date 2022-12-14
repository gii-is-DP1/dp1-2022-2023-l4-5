<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->


<nt4h:layout pageName="chat">
    <h2>"Chatea con ${receiver}"</h2>
    <div class="row">
        <div class="chat"></div>
        <form:form modelAttribute="chat" class="form-horizontal">
            <nt4h:inputField label="Content" name="content"/>
        </form:form>
    </div>
    <script src="/resources/js/chat.js" type="module"></script>
</nt4h:layout>


