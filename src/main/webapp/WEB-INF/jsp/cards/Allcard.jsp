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
        .text{
            padding-top: 10px;
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

        <h2 style="padding-top: 10px" >Explorer Cards</h2>
        <img class="card-img-top" src="\resources\images\abilities\wolf_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\diana_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\pick_abilites_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\rain_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\rapid_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\sharp_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\survival_abilities_front.png" alt="Card image cap">

        <h2 style="padding-top: 10px">Wizard Cards</h2>
        <img class="card-img-top" src="\resources\images\abilities\boladefuego_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\disparogelido_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\flechacorrosiva_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\orbecurativo_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\auraprotectora_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\golpebaston_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\proyectiligneo_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\reconstitucion_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\torrentedeluz_ability_front.png" alt="Card image cap">

        <h2 style="padding-top: 10px">Rogue Cards</h2>
        <img class="card-img-top" src="\resources\images\abilities\alcorazon_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\ballestaprecisa_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\enganar_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\enlassombras_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\robarbolsillos_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\saqueo_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\trampa_ability_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\ataquefurtivo_ability_front.png" alt="Card image cap">

    </div>
</nt4h:layout>
