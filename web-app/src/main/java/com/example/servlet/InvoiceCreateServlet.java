package com.example.servlet;

import com.example.entity.Invoice;
import com.example.entity.InvoiceItem;
import com.example.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class InvoiceCreateServlet extends HttpServlet {
  private EntityManagerFactory emf;

  public void init() {
    emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getSession().getAttribute("user") == null) {
      resp.sendRedirect("../login");
      return;
    }
    req.getRequestDispatcher("/WEB-INF/jsp/invoice_form.jsp").forward(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    User user = (User) req.getSession().getAttribute("user");
    if (user == null) {
      resp.sendRedirect("login");
      return;
    }

    // pull the three parallel arrays
    String[] names      = req.getParameterValues("name");
    String[] quantities = req.getParameterValues("quantity");
    String[] prices     = req.getParameterValues("unitPrice");

    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    Invoice inv = new Invoice();
    inv.setUser(user);

    if (names != null) {
      for (int i = 0; i < names.length; i++) {
        String nm = names[i].trim();
        if (!nm.isEmpty()) {
          InvoiceItem it = new InvoiceItem();
          it.setName(nm);
          try {
            it.setQuantity(Integer.parseInt(quantities[i].trim()));
          } catch (Exception e) {
            it.setQuantity(0);
          }
          try {
            it.setUnitPrice(Double.parseDouble(prices[i].trim()));
          } catch (Exception e) {
            it.setUnitPrice(0.0);
          }
          inv.addItem(it);
        }
      }
    }

    em.persist(inv);
    em.getTransaction().commit();

    // // ========= TEMPORARY DEBUG OUTPUT =========
    // // reload from DB
    // Invoice saved = em.find(Invoice.class, inv.getId());
    // em.close();

    // resp.setContentType("text/html;charset=UTF-8");
    // PrintWriter out = resp.getWriter();
    // out.println("<html><head><title>Debug: Saved Items</title></head><body>");
    // out.println("<h2>Invoice #" + saved.getId() + " — items from DB</h2>");
    // out.println("<pre>");
    // // build a simple JSON‐like array
    // out.print("[");
    // List<InvoiceItem> items = saved.getItems();
    // for (int i = 0; i < items.size(); i++) {
    //   InvoiceItem it = items.get(i);
    //   out.print(
    //     "{\"name\":\"" + it.getName() + "\"" +
    //     ",\"quantity\":"  + it.getQuantity() +
    //     ",\"unitPrice\":" + it.getUnitPrice() +
    //     "}"
    //   );
    //   if (i < items.size() - 1) out.print(", ");
    // }
    // out.println("]");
    // out.println("</pre>");
    // out.println("</body></html>");
    // // ==========================================

    // when you’re done, comment out the debug block above
    // and uncomment the redirect below:
    
    resp.sendRedirect("../invoices");
  }
}