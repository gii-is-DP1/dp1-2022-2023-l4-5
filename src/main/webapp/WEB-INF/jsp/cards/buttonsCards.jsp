<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="ButtonCards">
    <a href="/cards/warriorCards" class="btn btn-primary btn-lg btn-block" role="button">Warrior Cards</a>
    <a href="/cards/explorerCards" class="btn btn-primary btn-lg btn-block" role="button">Rogue Cards</a>
    <a href="/cards/wizardCards" class="btn btn-primary btn-lg btn-block" role="button">Priest Cards</a>
    <a href="/cards/rogueCards" class="btn btn-primary btn-lg btn-block" role="button">Mage Cards</a>
</nt4h:layout>
