<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="selectOrder">
    <jsp:body>
        <h2>
            <c:if test="${game['new']}">New </c:if> Game
        </h2>

        <form:form modelAttribute="game" class="form-horizontal" >
            <button id="waiting" hidden="hidden" type="submit">Create game</button>
        </form:form>
    </jsp:body>
</nt4h:layout>

<script type="module">
    import sendPetitionInInterval from "../../../../resources/js/petition.js";

    sendPetitionInInterval('/api/games/ready/', function (responseText) {
        const resultado = JSON.parse(responseText)
        const form = document.getElementById("waiting");
        if (resultado.isReady&& form.hasAttribute("hidden"))
            form.removeAttribute("hidden")
        else if (!resultado.isReady && !form.hasAttribute("hidden"))
            form.setAttribute("hidden", "hidden")
    })
</script>


