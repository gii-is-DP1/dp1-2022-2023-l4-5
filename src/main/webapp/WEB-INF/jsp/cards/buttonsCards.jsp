<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="ButtonCards">

    <form method="get" action="/cards/warriorCards">
        <button type="submit">Warrior Cards</button></form>
    <form method="get" action="/cards/explorerCards">
        <button type="submit">Explorer Cards</button></form>
    <form method="get" action="/cards/wizardCards">
        <button type="submit">Wizard Cards</button></form>
    <form method="get" action="/cards/rogueCards">
        <button type="submit">Rogue Cards</button></form>
    <form method="get" action="/cards/stagesCards">
        <button type="submit">Stages Cards</button></form>


</nt4h:layout>
