
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="WarriorCards">

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

        <h2 style="padding-top: 20px">Warrior Cards</h2>
        <img class="card-img-top" src="\resources\images\abilities\AAAAAAAA_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\all_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\back_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\charge_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\brutal_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\back_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\doble_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\shield_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\sword_abilities_front.png" alt="Card image cap">

    </div>
    <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
    <form method="get" action="/cards/buttonsCards">
        <button type="submit" style="height: 5rem; width: 20rem"  >Return</button></form>

</nt4h:layout>
