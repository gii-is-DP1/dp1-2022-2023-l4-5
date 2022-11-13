<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->

<nt4h:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/LogoNT4H.png" htmlEscape="true" var="logoImage"/>
            <img class="img-responsive" src="${logoImage}"/>
        </div>



        <p><h2>Descripcion</h2>
        No time for heroes es un juego de cartas cooperativo y estratégico basado en los juegos de rol clásicos. Se creó en el año 2016, a manos del diseñador español Rodrigo González, creador de otros juegos como Omertá, El Poder de la Mafia o Dice Drivin’
        <br/> Cada participante debe tomar el papel de uno de los 4 héroes arquetípicos (pícaro, guerrero, mago y arquero) con el objetivo de ser quien más puntos obtenga eliminando hordas de orcos en distintos escenarios. Para ello, dispondrán de un mazo de habilidades con el que poder atacar a los enemigos o causar determinados efectos y, a su vez, representará su nivel de energía. Las cartas de habilidad se van descartando si se recibe daño o con algunos efectos y, una vez que el montón se agote, el héroe perderá un punto de vida, siendo derrotado si pierde todas. Cuando se derrote un orco este proporcionará puntos de gloria y, en ocasiones, monedas con las que poder adquirir equipamiento nuevo. El juego termina cuando se derrote al enemigo final, el Señor de la Guerra, y el ganador será el que más puntos de gloria haya acumulado.
        <br/> Las partidas suelen durar unos 45 minutos aproximadamente, pudiendo competir en ellas entre 2 y 4 personas. También si se desea, existe un modo de juego individual.</p>
        <br/>
        <p><h2>Participantes: </h2></p>
        <c:forEach items="${people}" var="p">
            <li>${p.firstName} ${p.lastName}</li>
        </c:forEach>
    </div>
</nt4h:layout>
