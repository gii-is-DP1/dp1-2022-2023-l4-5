<%@ page import="org.springframework.samples.nt4h.user.User" %>
<%@ page import="org.springframework.samples.nt4h.game.Game" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="cards">

    <style>
        .card-column {
            padding-left: 5px;
            padding-right: 10px;
            padding-top: 15px;
        }

        .card-img-top {
            width: 24%;
            padding-left: 5px;
            padding-right: 10px;
            padding-top: 15px;
        }
    </style>

    <div class="card-column">

        <h2>Warrior Cards</h2>
        <img class="card-img-top" src="\resources\images\abilities\charge_abilities_front.png" alt="Card image cap">

        <img class="card-img-top" src="\resources\images\abilities\brutal_abilities_front.png" alt="Card image cap">


        <img class="card-img-top" src="\resources\images\abilities\back_abilities_front.png" alt="Card image cap">


        <img class="card-img-top" src="\resources\images\abilities\charge_abilities_front.png" alt="Card image cap">


        <img class="card-img-top" src="\resources\images\abilities\brutal_abilities_front.png" alt="Card image cap">


        <img class="card-img-top" src="\resources\images\abilities\back_abilities_front.png" alt="Card image cap">

    </div>
</nt4h:layout>
