<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="detailedStatistics">
    <h1>${name}</h1>
    <br><c:out value="Min: ${min}"/></br>
    <br><c:out value="Max: ${max}"/></br>
    <br><c:out value="Average: ${average}"/></br>
</nt4h:layout>
