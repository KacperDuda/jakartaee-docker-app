<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
  <link rel="stylesheet" href="<c:url value='/static/css/style.css'/>" />
</head>
<body>
  <h2>New Invoice</h2>
  <form method="post">
    <table>
      <tr>
        <th>#</th>
        <th>Product Name</th>
        <th>Quantity</th>
        <th>Unit Price</th>
      </tr>
      <% for (int i = 1; i <= 20; i++) { %>
        <tr>
          <td><%= i %></td>
          <td><input type="text" name="name" maxlength="100"/></td>
          <td><input type="number" name="quantity" min="0" step="1"/></td>
          <td><input type="number" name="unitPrice" min="0" step="0.01"/></td>
        </tr>
      <% } %>
    </table>
    <button type="submit">Create</button>
  </form>
  <a href="../invoices">Back</a>
</body>
</html>