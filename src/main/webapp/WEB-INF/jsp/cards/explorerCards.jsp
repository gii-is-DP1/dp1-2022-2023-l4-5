
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="ExplorerCards">

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

        <h2 style="padding-top: 10px" >Explorer Cards</h2>
        <img class="card-img-top" src="\resources\images\abilities\wolf_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\diana_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\pick_abilites_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\rain_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\rapid_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\sharp_abilities_front.png" alt="Card image cap">
        <img class="card-img-top" src="\resources\images\abilities\survival_abilities_front.png" alt="Card image cap">

    </div>

    <br/> <!-- salto de linea/ meter un espacio en blanco entre texto -->
    <form method="get" action="/cards/buttonsCards">
        <button type="submit" style="height: 5rem; width: 20rem"  >Return</button></form>

</nt4h:layout>
