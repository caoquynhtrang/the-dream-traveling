/*
 * To change this license header, choose Lic  ense Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trangcq.bookingitem.BookingItemDAO;
import trangcq.cart.CartObject;
import trangcq.traveltour.TravelTourDAO;

/**
 *
 * @author USER
 */
@WebServlet(name = "UpdateTourServlet", urlPatterns = {"/UpdateTourServlet"})
public class UpdateTourServlet extends HttpServlet {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(UpdateTourServlet.class);
    private final String VIEW_PAGE = "view.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = VIEW_PAGE;
        String txtTourId = request.getParameter("txtTourId");
        String txtAmount = request.getParameter("txtAmount");
        try {
            HttpSession session = request.getSession();
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartObject();
            }
            int tourId = Integer.parseInt(txtTourId);
            int amount = Integer.parseInt(txtAmount);

            BookingItemDAO bookingItemDAO = new BookingItemDAO();
            TravelTourDAO travelDAO = new TravelTourDAO();

            int totalBooked = bookingItemDAO.countTotalBookedTour(tourId);
            int tourQuota = travelDAO.getTourQuota(tourId);

            if (amount > 0 && tourQuota >= totalBooked + amount) {
                cart.updateItem(tourId, amount);
                session.setAttribute("CART", cart);
            } else {
                List<String> confirmErrs = new ArrayList<>();
                confirmErrs.add("Invalid tour amount: (" + (tourQuota - totalBooked) + " remainings)");
                request.setAttribute("CONFIRM_ERROR", confirmErrs);
            }

        } catch (SQLException ex) {
            LOGGER.fatal(ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.fatal(ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
            out.close();
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
