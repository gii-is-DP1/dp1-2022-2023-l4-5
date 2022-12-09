<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Action decision">
    <h2>Action decision</h2>
    <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
        <nt4h:selectField name="phase" label="Phases in turn?" names="${turns}" size="8"/>
        <button class="btn btn-default" type="submit">Action chosen</button>
    </form:form>

</nt4h:layout>
