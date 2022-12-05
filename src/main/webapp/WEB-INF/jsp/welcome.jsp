<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nt4h:layout pageName="home">
    <html>
    <style>
        .colorletter{
            color:"white";
        }
        .background{
            background-image: url("/resources/images/fondo_negro.jpg");
            background-repeat:repeat;
            background-position: revert;
            background-size: 100%;
        }
    </style>
    <head>
        <!--
        Vista del Administrador
        -->
        <div>
            <sec:authorize access="hasAnyAuthority('DOKTOL')">
            </sec:authorize>
        </div>
        <!--
        Vista del Administrador
        -->
        <sec:authorize access="hasAnyAuthority('USER')">
            <h1>HOLA USUARIO BUENOS DIAS</h1>
        </sec:authorize>
    </head>

    <body class="background">
    <sec:authorize access="!isAuthenticated()">
        <center>
            <div class="container">
                <h1 style = "color: white; text-shadow: 3px 3px 6px #000000;"><FONT FACE="verdana" SIZE=15><fmt:message key="welcome"/></FONT></h1>
                <h2 style="color: white; text-shadow: 3px 3px 6px #000000;"><font face="verdana" SIZE="5">${title}</font></h2>
                <img src="/resources/images/AAA.jpg" id="miImagen" height="400rem" width="700rem">
                <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
                <div class="row">
                    <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
                    <h5>
                        <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
                        No time for heroes es un juego de cartas cooperativo y estratégico basado en los juegos de rol clásicos. Se
                        creó en el año 2016, a manos del diseñador español Rodrigo González, creador de otros juegos como Omertá, El
                        Poder de la Mafia o Dice Drivin’
                        <br/> Cada participante debe tomar el papel de uno de los 4 héroes arquetípicos (pícaro, guerrero, mago y
                        arquero) con el objetivo de ser quien más puntos obtenga eliminando hordas de orcos en distintos escenarios.
                        Para ello, dispondrán de un mazo de habilidades con el que poder atacar a los enemigos o causar determinados
                        efectos y, a su vez, representará su nivel de energía. Las cartas de habilidad se van descartando si se
                        recibe daño o con algunos efectos y, una vez que el montón se agote, el héroe perderá un punto de vida,
                        siendo derrotado si pierde todas. Cuando se derrote un orco este proporcionará puntos de gloria y, en
                        ocasiones, monedas con las que poder adquirir equipamiento nuevo. El juego termina cuando se derrote al
                        enemigo final, el Señor de la Guerra, y el ganador será el que más puntos de gloria haya acumulado.
                        <br/> Las partidas suelen durar unos 45 minutos aproximadamente, pudiendo competir en ellas entre 2 y 4
                        personas. También si se desea, existe un modo de juego individual.
                        </span>
                    </h5>
                </div>
            </div>
            <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
            <div>-----------------------------------------------------------------------------------------------------------------
                <h1 style = "color: white; text-shadow: 3px 3px 6px #000000;">Manuales del juego<FONT FACE="verdana" SIZE=15></FONT></h1>
                <form method="get" action="https://drive.google.com/file/d/1ElArQTTfUjz2GNEQ8lG6dfOwikvFWY-O/view">
                    <button type="submit"><img src="/resources/images/Boton_reglas_del_juego.PNG" width="350" height="75" /></button></form>
                <form method="get" action="https://drive.google.com/file/d/1FDUgfsXFJ7DxGDmtIg76us_5u8sALhHx/view">
                    <button type="submit"><img src="/resources/images/Boton_reglas_multiclase.PNG" width="350" height="75" /></button></form>
            </div>
            <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
            <div class="column-block"> -----------------------------------------------------------------------------------------------------------------
                <h1 style = "color: white; text-shadow: 3px 3px 6px #000000;">Participantes<FONT FACE="verdana" SIZE=15></FONT></h1>
                <h2 style="color: black; text-shadow: antiquewhite";>
                    <c:forEach items="${people}" var="p">
                        <p><h5 style="color: white; text-shadow: 3px 3px 6px #000000;"><font face="verdana" SIZE="3">${p.firstName} ${p.lastName}</font></h5></p>
                    </c:forEach></h2>
            </div>
        </center>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
    <center>
        <div>-----------------------------------------------------------------------------------------------------------------
            <h1 style = "color: white; text-shadow: 3px 3px 6px #000000;">¿Jugamos?<FONT FACE="verdana" SIZE=15></FONT></h1>

            <form method="get" action="/games/new">
                <button type="submit"><img src="/resources/images/Boton_create.PNG" width="280" height="75" /></button></form>
            <form method="get" action="/games">
                <button type="submit"><img src="/resources/images/Boton_find.PNG" width="280" height="75" /></button></form>
        </div>
        <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
        <div>-----------------------------------------------------------------------------------------------------------------
            <h1 style = "color: white; text-shadow: 3px 3px 6px #000000;">Reglas del juego<FONT FACE="verdana" SIZE=15></FONT></h1>
            <form method="get" action="https://drive.google.com/file/d/1ElArQTTfUjz2GNEQ8lG6dfOwikvFWY-O/view">
                <button type="submit"><img src="/resources/images/Boton_reglas_del_juego.PNG" width="350" height="75" /></button></form>
            <form method="get" action="https://drive.google.com/file/d/1FDUgfsXFJ7DxGDmtIg76us_5u8sALhHx/view">
                <button type="submit"><img src="/resources/images/Boton_reglas_multiclase.PNG" width="350" height="75" /></button></form>
        </div>
    </center>
    </sec:authorize>
    </header>
    </body>
    </html>
</nt4h:layout>
