<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Тест - сторінка реєстрації безуспішних пошукових запитів</title>
</head>
<body>
<h1>Сторінка реєстрації безуспішних пошукових запитів</h1>
<p><a href="testHome">Повернутися на домашню тестову сторінку</a></p>
<form:form modelAttribute="search" action="testAddSearchQuery">
	<form:input path="query"/>
	<input type="submit" value="Зареєструвати">
</form:form>
</body>
</html>
