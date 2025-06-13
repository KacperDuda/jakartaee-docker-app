package com.example;
import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class EMFListener implements ServletContextListener {
  private EntityManagerFactory emf;
  public void contextInitialized(ServletContextEvent sce) {
    emf = Persistence.createEntityManagerFactory("invoicesPU");
    sce.getServletContext().setAttribute("emf", emf);
  }
  public void contextDestroyed(ServletContextEvent sce) {
    emf.close();
  }
}