<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="allStatistics">

<c:for begin="0" end="${allStatistics.size()}" var="section">
  <a href="/statistics/${section}" class="btn btn-primary btn-lg btn-block" role="button">${allStatistics[section].getValue0()}</a>
</c:for>

</nt4h:layout>


