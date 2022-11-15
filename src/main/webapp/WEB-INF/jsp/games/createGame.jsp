<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="CreateGames">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>

    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${game['new']}">New </c:if> Game
        </h2>

        <form:form modelAttribute="game" class="form-horizontal" id="add-game-form">
            <div class="form-group has-feedback">
                <nt4h:inputField label="Name" name="name"/>
                <nt4h:inputField label="Password" name="password"/>
                <nt4h:selectField name="Mode" label="Mode" names="${mode}" size="2"/>
                <nt4h:selectField name="MaxPlayers" label="Max players" names="${[2,3,4]}" size="3"/>
                <form:checkbox path="hasStages" label="Play with stages"/>
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Create game</button>
                </div>
            </div>

        </form:form>
    </jsp:body>
</nt4h:layout>
