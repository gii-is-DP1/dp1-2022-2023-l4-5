<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Hero selection">
    <h2>Hero Selection</h2>
    <form:form modelAttribute= "heroInGame" >
        <div class="form-group has-feedback">
        <nt4h:selectField name="actualHealth" label="Hero" names="${[1,2,3]}" size="3"/>
        <button class="btn btn-default" type="submit">Confirm hero</button>
        </div>
    </form:form>
</nt4h:layout>
