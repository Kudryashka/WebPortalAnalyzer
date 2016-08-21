<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<html>
<head>
	<title>Тест - сторінка з різноманітними посиланнями</title>
</head>
<body>
<h1>Сторінка з різноманітними посиланнями</h1>
<p><a href="testHome">Повернутися на домашню тестову сторінку</a></p>
<h2>Звичайні посилання (&lt;a&gt;)</h2>
<div>
	<a href="testHome">Саме звичайне посилання</a>
</div>
<div>
	<a>Посилання без HREF</a>
</div>
<div>
	<a href="testPageWithLinks">Рекурсивне посилання</a>
</div>
<div>
	<script>
		document.write('<a href="testHome">Посилання згенеровано за допомогою JS</a>');
	</script>
</div>
<div id="anchor-with-timeout-holder">
	<script>
		var appendAnchor = function() {
			var holder = document.getElementById('anchor-with-timeout-holder');
			holder.innerHTML += '<a href="testHome">Посилання з\'явилося через 400ms</a>';
		};
		window.setTimeout(appendAnchor, 400);
	</script>
</div>
<!-- TODO add another link types -->
</body>
</html>
