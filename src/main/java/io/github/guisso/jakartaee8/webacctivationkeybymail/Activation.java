package io.github.guisso.jakartaee8.webacctivationkeybymail;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@WebServlet(
        name = "Activation",
        urlPatterns = {"/Activation"})
public class Activation extends HttpServlet {
    
    @Inject
    private UserBean userBean;

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String key = request.getParameter("activationKey");
        
        if (email == null || key == null) {
            response.sendRedirect("checkemail.xhtml");
            
        } else {
            User user = FakeDatabase.findByEmail(email);
            
            if (user != null && user.getKey().toString().equals(key)) {
                user.setActive(true);
                FakeDatabase.saveUser(user);
                userBean.setEmail(email);
                response.sendRedirect("activation.xhtml");
            } else {
                response.sendRedirect("checkemail.xhtml");
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
        public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
