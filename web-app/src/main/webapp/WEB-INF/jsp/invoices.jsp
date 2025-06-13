<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value='/static/css/style.css'/>" />
</head>
<body>
    <p>Welcome, <b>${username}</b>!</p>

    <h2>Your Invoices</h2>
    <a href="invoice/create">New Invoice</a> | <a href="logout">Logout</a>
    <table>
        <tr>
            <th>ID</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="inv" items="${invoices}">
            <tr>
                <td>${inv.id}</td>
                <td><a href="invoice/pdf?id=${inv.id}">PDF</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>