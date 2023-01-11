<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="detailedStatistics">
    <h1>${name}</h1>
    <c:out value="<b>Min:</b> ${min}">
    <c:out value="<b>Max:</b> ${max}">
    <c:out value="<b>Average:</b> ${average}">
</nt4h:layout>
