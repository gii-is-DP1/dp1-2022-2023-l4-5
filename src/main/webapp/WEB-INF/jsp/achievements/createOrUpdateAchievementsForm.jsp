<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="achievements">
    <h2>
        <c:if test="${achievement['new']}">New </c:if> Achievement
    </h2>
    <form:form modelAttribute="achievement" class="form-horizontal" id="add-achievement-form">
        <div class="form-group has-feedback">
            <nt4h:inputField label="Name" name="name"/>
            <nt4h:inputField label="Description" name="description"/>
            <nt4h:inputField label="Image" name="image"/>
            <nt4h:inputField label="Threshold" name="threshold"/>
            <nt4h:selectField name="achievementType" label="Achievement Type" names="${achievementType}" size="8"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${achievement['new']}">
                        <button class="btn btn-default" type="submit">Add Achievement</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Achievement</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</nt4h:layout>

