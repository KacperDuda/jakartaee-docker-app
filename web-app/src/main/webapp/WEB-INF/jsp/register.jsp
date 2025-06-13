<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head> <link rel="stylesheet" href="<c:url value='/static/css/style.css'/>" /></head>
<body><h2>Register</h2>
<form method="post">
  <input name="username" placeholder="Username" required><br>
  <input type="password" name="password" placeholder="Password" required><br>
  <button>Register</button>
  <a href="login">Login</a>
</form>
<c:if test="${not empty error}"><p class="error">${error}</p></c:if>
</body></html>