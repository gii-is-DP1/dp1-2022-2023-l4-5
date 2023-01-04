<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Market phase">
    <h1>Action decision</h1>
    <h1>${game.currentPlayer}`s Turn</h1>
    <h2>Buy a product</h2>
    <form:form modelAttribute="productInGame" class="form-horizontal" id="product-selection-form">
        <div class="container">
            <c:forEach begin="0" step="1" end="${productsOnSale.size()-1}" var="i">
                <div class="col-sm-5">
                    <form:radiobutton id="i" type="radio" name="hero" value="${productsOnSale.get(i)}" />
                    <img  class="card-img-top" src="${productsOnSale.get(i).getFrontImage()}">
                </div>
            </c:forEach>
        </div>
        <button class="btn btn-default" type="submit">Buy Product</button>
    </form:form>
    <div class="nextTurn"></div>
    <script src="/resources/js/currentTurn.js" type="module"></script>
</nt4h:layout>
