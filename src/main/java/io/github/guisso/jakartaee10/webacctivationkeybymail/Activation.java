package io.github.guisso.jakartaee10.webacctivationkeybymail;

import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@WebServlet(
        name = "Activation",
        urlPatterns = {"/Activation"})
public class Activation
        extends HttpServlet {

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
// </editor-fold>

}
