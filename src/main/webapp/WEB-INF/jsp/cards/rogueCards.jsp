
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="RogueCards">

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

    <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
    <form method="get" action="/cards/buttonsCards">
        <button type="submit" style="height: 5rem; width: 20rem"  >Return</button></form>
</nt4h:layout>
