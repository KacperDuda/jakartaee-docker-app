package com.example.servlet;

import com.example.entity.Invoice;
import com.example.entity.InvoiceItem;
import com.example.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InvoicePdfServlet extends HttpServlet {
  private EntityManagerFactory emf;
  private String pdfUrl;

  @Override
  public void init() {
    emf = (EntityManagerFactory) getServletContext().getAttribute("emf");

    // Attempt to read the context-param; if it's of the form ${env.VAR}
    // resolve against System.getenv("VAR"), otherwise take it literally.
    String raw = getServletContext().getInitParameter("PDF_SERVICE_URL");
    if (raw != null && raw.matches("\\$\\{env\\.[A-Za-z0-9_]+\\}")) {
      String varName = raw.substring(6, raw.length() - 1);
      pdfUrl = System.getenv(varName);
    } else {
      pdfUrl = raw;
    }

    if (pdfUrl == null || !pdfUrl.contains("://")) {
      throw new IllegalStateException(
        "PDF_SERVICE_URL is not configured properly: " + raw);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    User user = (User) req.getSession().getAttribute("user");
    if (user == null) {
      resp.sendRedirect("../login");
      return;
    }

    long id = Long.parseLong(req.getParameter("id"));
    EntityManager em = emf.createEntityManager();
    Invoice inv = em.createQuery(
        "SELECT i FROM Invoice i LEFT JOIN FETCH i.items WHERE i.id = :id",
        Invoice.class)
      .setParameter("id", id)
      .getResultStream()
      .findFirst()
      .orElse(null);
    em.close();

    if (inv == null || !inv.getUser().getId().equals(user.getId())) {
      resp.sendError(404);
      return;
    }

    StringBuilder itemsJson = new StringBuilder();
    for (InvoiceItem item : inv.getItems()) {
      if (itemsJson.length() > 0) itemsJson.append(",");
      itemsJson.append(String.format(
        "{\"name\":\"%s\",\"quantity\":%d,\"unitPrice\":%s}",
        item.getName(),
        item.getQuantity(),
        item.getUnitPrice()
      ));
    }
    String json = String.format(
      "{\"id\":%d,\"username\":\"%s\",\"items\":[%s]}",
      inv.getId(),
      user.getUsername(),
      itemsJson
    );

    URL url = new URL(pdfUrl);
    HttpURLConnection c = (HttpURLConnection) url.openConnection();
    c.setRequestMethod("POST");
    c.setDoOutput(true);
    c.setRequestProperty("Content-Type", "application/json");
    try (OutputStream os = c.getOutputStream()) {
      os.write(json.getBytes());
    }

    resp.setContentType("application/pdf");
    try (InputStream is = c.getInputStream();
         OutputStream os = resp.getOutputStream()) {
      is.transferTo(os);
    }
  }
}