<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nt4h:layout pageName="home">
    <body class="background">
    <sec:authorize access="!isAuthenticated()">
        <div style="text-align: center;">
            <div class="container">
                <h1 style="color: white; text-shadow: 3px 3px 6px #000000;"><span FACE="hola"><fmt:message key="welcome"/></span></h1>
                <h2 style="color: white; text-shadow: 3px 3px 6px #000000;"><span style="font-family: verdana;">${title}</span>
                </h2>
                <img src="/resources/images/AAA.jpg" id="miImagen" height="400rem" width="700rem">
                <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
                <div class="row">
                    <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
                    <h5 style="text-align:justify">
                        No time for heroes es un juego de cartas cooperativo y estratégico basado en los juegos de rol
                        clásicos. Se creó en el año 2016, a manos del diseñador español Rodrigo González, creador de otros juegos
                        como Omertá, El Poder de la Mafia o Dice Drivin’.
                    </h5>
                    <h5 style="text-align:justify;">
                        Cada participante debe tomar el papel de uno de los 4 héroes arquetípicos (pícaro, guerrero, mago y
                        arquero) con el objetivo de ser quien más puntos obtenga eliminando hordas de orcos en distintos
                        escenarios. Para ello, dispondrán de un mazo de habilidades con el que poder atacar a los enemigos o causar
                        determinados efectos y, a su vez, representará su nivel de energía. Las cartas de habilidad se van descartando si
                        se recibe daño o con algunos efectos y, una vez que el montón se agote, el héroe perderá un punto de
                        vida, siendo derrotado si pierde todas. Cuando se derrote un orco este proporcionará puntos de gloria y,
                        en ocasiones, monedas con las que poder adquirir equipamiento nuevo. El juego termina cuando se derrote
                        al enemigo final, el Señor de la Guerra, y el ganador será el que más puntos de gloria haya acumulado.
                    </h5>
                    <h5 style="text-align:justify;">
                        Las partidas suelen durar unos 45 minutos aproximadamente, pudiendo competir en ellas entre 2 y 4
                        personas. También si se desea, existe un modo de juego individual.
                    </h5>
                </div>
            </div>
            <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
        </div>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
    <div style="text-align: center;">
        <div>
            <hr>
            <h1 style="color: white; text-shadow: 3px 3px 6px #000000;">¿Jugamos?<span FACE="verdana" SIZE=15></span>
            </h1>
            <a href="/games/new">
                <img src="/resources/images/Boton_create.PNG" width="280rem" height="75rem" alt="Create"
                     onmouseover="this.src='https://pbs.twimg.com/profile_images/1292184934489128961/DFawhH9s_400x400.jpg'"
                     onmouseout="this.src='/resources/images/Boton_create.PNG'">
            </a>
            <a href="/games">
                <img src="/resources/images/Boton_find.PNG" width="280rem" height="75rem" alt="Find"
                     onmouseover="this.src='https://pbs.twimg.com/profile_images/1292184934489128961/DFawhH9s_400x400.jpg'"
                     onmouseout="this.src='/resources/images/Boton_find.PNG'"/>
            </a>
        </div>
        <br/>
    </div>
    </sec:authorize>
    <div>
        <hr>
        <div style="text-align: center;">
            <h1 style="color: white; text-shadow: 3px 3px 6px #000000;">Manuales del juego<span FACE="verdana" SIZE=15></span></h1>
            <a href="https://drive.google.com/file/d/1ElArQTTfUjz2GNEQ8lG6dfOwikvFWY-O/view">
                <img src="/resources/images/Boton_reglas_del_juego.PNG" width="350 rem" height="75rem" alt="Reglas del juego"
                     onmouseover="this.src='https://pbs.twimg.com/profile_images/1292184934489128961/DFawhH9s_400x400.jpg'"
                     onmouseout="this.src='/resources/images/Boton_reglas_del_juego.PNG'"/>
            </a>
            <a href="https://drive.google.com/file/d/1FDUgfsXFJ7DxGDmtIg76us_5u8sALhHx/view">
                <img src="/resources/images/Boton_reglas_multiclase.PNG" width="350rem" height="75rem" alt="Reglas del juego (multiclase)"
                     onmouseover="this.src='https://pbs.twimg.com/profile_images/1292184934489128961/DFawhH9s_400x400.jpg'"
                     onmouseout="this.src='/resources/images/Boton_reglas_multiclase.PNG'"/>
            </a>
        </div>
    </div>
    <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
    <div class="column-block" style="text-align: center;">
        <hr>
        <h1 style="color: white; text-shadow: 3px 3px 6px #000000;">Participantes<span FACE="verdana" SIZE=15></span>
        </h1>
        <h2 style="color: black; text-shadow: antiquewhite" >
            <c:forEach items="${people}" var="p">
                <h5 style="color: white; text-shadow: 3px 3px 6px #000000;"><span SIZE="3" style="font-family: verdana; ">${p.firstName} ${p.lastName}</span></h5>
            </c:forEach></h2>
    </div>
    </body>
</nt4h:layout>

<style>
    .background {
        background-image: url("/resources/images/fondo_negro.jpg");
        background-repeat: no-repeat;
        background-size: cover;
    }
</style>
