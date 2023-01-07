<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Market phase">
    <style>
        .pointer {
            display: flex;
            justify-content: center;
        }
    </style>
    <h1>Action decision</h1>
    <h1>${currentPlayer}`s Turn</h1>
    <h2>Buy a product</h2>
    <c:if test="${!loggedPlayer.isNew()}">
        <form:form modelAttribute="newProductInGame" class="form-horizontal" id="product-selection-form">
            <div class="container">
                <c:if test="${productsOnSale.size()!=0}">
                    <div class="pointer">
                        <c:forEach var="i" begin="0" end="${productsOnSale.size()-1}">
                            <c:set var="product" value="${productsOnSale[i]}" scope="page"/>
                            <!-- Comprar no fufa -->
                            <div class="col-sm-2">
                                <nt4h:radioButtom name="product" element="${product.id}"
                                                  frontImage="${product.frontImage}"
                                                  i="${i}0" image="/resources/images/muszka.png"/>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${productsOnSale.size()==0}">
                    <div style="display: flex; justify-content: center;">
                        <h1>There are no products on sale</h1>
                    </div>
                </c:if>
            </div>
            <c:if test="${productsOnSale.size()!=0}">
                <button class="btn btn-default" type="submit">buy Product</button>
            </c:if>
        </form:form>
    </c:if>
    <c:if test="${loggedPlayer.isNew()}">
        <div class="container">
            <div style="display: flex;justify-content: center;">
                <c:if test="${productsOnSale.size()}">
                    <c:forEach var="i" begin="0" end="${productsOnSale.size()-1}">
                        <c:set var="product" value="${productsOnSale[i]}" scope="page"/>
                        <div class="col-sm-2">
                            <img src="${product.frontImage}">
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${productsOnSale.size()==0}">
                    <h1>There are no products on sale</h1>
                </c:if>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="chatGroup"></div>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
                <nt4h:inputField label="Content" name="content"/>
            </form:form>
        </c:if>
    </div>
    <div class="nextTurn"></div>
    <script src="/resources/js/chatGroup.js" type="module"></script>
    <script src="/resources/js/currentTurn.js" type="module"></script>
    <script src="/resources/js/radioButtom.js" type="module">
</nt4h:layout>
