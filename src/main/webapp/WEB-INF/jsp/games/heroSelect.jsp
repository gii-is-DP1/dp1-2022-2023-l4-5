<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="hero selection">
    <h2>Hero Selection</h2>
    <form:form modelAttribute="hero" class="form-horizontal" id="choose-hero-form">
        <nt4h:selectField name="hero" label="Hero in game" names="${heroes}" size="8"/>
        <button class="btn btn-default" type="submit">Hero choose</button>
    </form:form>
</nt4h:layout>
