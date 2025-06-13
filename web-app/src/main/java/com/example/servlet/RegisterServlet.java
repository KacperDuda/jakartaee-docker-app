package com.example.servlet;
import com.example.entity.User;
import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
public class RegisterServlet extends HttpServlet {
  private EntityManagerFactory emf;
  public void init() { emf = (EntityManagerFactory) getServletContext().getAttribute("emf"); }
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req,resp);
  }
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String u = req.getParameter("username"), p = req.getParameter("password");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
        tx.begin(); // Always start transaction!
        User user = new User();
        user.setUsername(u);
        user.setPassword(p);
        em.persist(user);
        tx.commit();
        resp.sendRedirect("login");
    } catch(Exception e) {
        if (tx.isActive()) { // <--- This is important!
            tx.rollback();
        }
        req.setAttribute("error", "Username taken: " + e.getMessage());
        doGet(req, resp);
    } finally {
        em.close();
    }
}
}