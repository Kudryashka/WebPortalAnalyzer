<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<html>
<head>
  <title>Тест - інформація про користувача.</title>
</head>
<body>
<h1>На цій сторінці за Вами шпигують</h1>
<a href="testHome">Повернутися на домашню тестову сторінку</a>
<script type="text/javascript">
function getXmlHttp() {
  var xmlHttp;
  try {
    xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
  } catch (e) {
    try {
      xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    } catch (e2) {
      xmlHttp = false;
    }
  }
  if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
    xmlHttp = new XMLHttpRequest();
  }
  return xmlHttp;
}
function getIP(callback) {
  var xmlHttp = getXmlHttp();
  xmlHttp.open('GET', 'http://localhost:8080/WebPortalAnalyzer/api/v1.1/usersInfos/ip', true);
  xmlHttp.onreadystatechange = function() {
    if (xmlHttp.readyState == 4) {
      if (xmlHttp.status == 200) {
        var ipResponse = JSON.parse(xmlHttp.responseText);
        callback(ipResponse.ip);
      }
    }
  }
  xmlHttp.send(null);
}
function sendInfo() {
  var latitude;
  var longitude;
  function processAfterGeolocation() {
    getIP(function(ip) {
      var info = {
        ip: ip
      };
      if (latitude && longitude) {
        info.latitude = latitude;
        info.longitude = longitude;
      }
      var xmlHttp = getXmlHttp();
      xmlHttp.open('PUT', 'http://localhost:8080/WebPortalAnalyzer/api/v1.1/usersInfos', true);
      xmlHttp.setRequestHeader('Content-Type', 'application/json; charset=utf-8');
      xmlHttp.send(JSON.stringify(info));
    });	
  }
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      function(position) {
        latitude = position.coords.latitude;
        longitude = position.coords.longitude;
        processAfterGeolocation();
      },
      function(error) {
        switch(error.code) {
        case error.PERMISSION_DENIED:
          console.error("Can not determine location. Cause: User denied the request for Geolocation.");
          break;
        case error.POSITION_UNAVAILABLE:
          console.error("Can not determine location. Cause: Location information is unavailable.");
          break;
        case error.TIMEOUT:
          console.error("Can not determine location. Cause: The request to get user location timed out.");
          break;
        case error.UNKNOWN_ERROR:
          console.error("Can not determine location. Cause: An unknown error occurred.");
          break;
        }
        processAfterGeolocation();
      }
    );
  }	
}
function sendRawRequest() {
  var xmlHttp = getXmlHttp();
  xmlHttp.open('GET', 'http://localhost:8080/WebPortalAnalyzer/api/v1.1/usersInfos/raw', true);
  xmlHttp.send(null);
}
</script>
</body>
</html>
