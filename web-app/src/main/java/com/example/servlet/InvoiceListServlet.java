package com.example.servlet;
import com.example.entity.*;
import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class InvoiceListServlet extends HttpServlet {
    private EntityManagerFactory emf;
    public void init() {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }
        EntityManager em = emf.createEntityManager();
        List<Invoice> invoices = em.createQuery(
            "SELECT i FROM Invoice i WHERE i.user.id = :uid", Invoice.class)
            .setParameter("uid", user.getId())
            .getResultList();
        req.setAttribute("invoices", invoices);
        req.setAttribute("username", user.getUsername()); // Pass username to JSP
        req.getRequestDispatcher("/WEB-INF/jsp/invoices.jsp").forward(req, resp);
        em.close();
    }
}