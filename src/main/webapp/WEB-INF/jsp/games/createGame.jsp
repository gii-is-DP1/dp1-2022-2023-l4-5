<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="CreateGames">
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


                <petclinic:inputField label="Password" name="password"/>

            <div class="form-group">
                Mode:
                <select name="Mode">
                    <option value="1">UNI_CLASS</option>
                    <option value="2">MULTI_CLASS</option>
                </select>
            </div>
            <div class="form-group">
                Accessibility:
                <select name="Accessibility">
                    <option value="1">Private</option>
                    <option value="2">Public</option>
                </select>
            </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Create game</button>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>