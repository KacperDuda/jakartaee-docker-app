package com.example.servlet;
import com.example.entity.User;
import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
public class LoginServlet extends HttpServlet {
  private EntityManagerFactory emf;
  public void init() { emf = (EntityManagerFactory) getServletContext().getAttribute("emf"); }
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,resp);
  }
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException {
    String u=req.getParameter("username"), p=req.getParameter("password");
    EntityManager em=emf.createEntityManager();
    try {
      TypedQuery<User> q=em.createQuery("SELECT u FROM User u WHERE u.username=:u AND u.password=:p",User.class);
      q.setParameter("u",u).setParameter("p",p);
      User user=q.getSingleResult();
      req.getSession().setAttribute("user",user);
      resp.sendRedirect("invoices");
    } catch(Exception e) {
      req.setAttribute("error","Bad login");
      doGet(req,resp);
    } finally { em.close(); }
  }
}