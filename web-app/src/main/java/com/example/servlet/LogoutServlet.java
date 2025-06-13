package com.example.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
public class LogoutServlet extends HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException {
    req.getSession().invalidate();
    resp.sendRedirect("login");
  }
}