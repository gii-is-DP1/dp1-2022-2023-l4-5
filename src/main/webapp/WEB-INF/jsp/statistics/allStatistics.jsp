<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="allStatistics">

<c:forEach begin="0" end="${allStatistic.size()-1}" var="section">
  <a href="/statistics/${section}" class="btn btn-primary btn-lg btn-block" role="button">${allStatistic[section].getValue0()}</a>
</c:forEach>

</nt4h:layout>


