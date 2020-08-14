/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
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
@WebServlet(name = "AddItemToCartServlet", urlPatterns = {"/AddItemToCartServlet"})
public class AddItemToCartServlet extends HttpServlet {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AddItemToCartServlet.class);
    private final String URL_SEARCH_PAGE = "search";

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
        String url = "";
        String tourId = request.getParameter("tourId");
        String searchLocation = request.getParameter("txtSearchLocation");
        String searchFromPrice = request.getParameter("txtFromPrice");
        String searchToPrice = request.getParameter("txtToPrice");
        String searchFromDate = request.getParameter("txtFromDate");
        String searchToDate = request.getParameter("txtToDate");
        String pageNumber = request.getParameter("page");
        try {
            HttpSession session = request.getSession();
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartObject();
            }

            int id = -1;
            try {
                id = Integer.parseInt(tourId);
            } catch (Exception e) {
            }
            BookingItemDAO bookingItemDAO = new BookingItemDAO();
            TravelTourDAO travelDAO = new TravelTourDAO();

            int totalBooked = bookingItemDAO.countTotalBookedTour(id);

            Map<Integer, Integer> items = cart.getItems();
            int currentAmountInCart = 0;
            if (items != null) {
                Integer item = items.get(id);
                if (item != null) {
                    currentAmountInCart = item;
                }

            }

            int tourQuota = travelDAO.getTourQuota(id);

            if (tourQuota >= totalBooked + currentAmountInCart) {
                url += URL_SEARCH_PAGE + 
                        "?txtSearchLocation=" + searchLocation
                        + "&txtFromPrice=" + searchFromPrice
                        + "&txtToPrice=" + searchToPrice
                        + "&txtFromDate=" + searchFromDate
                        + "&txtToDate=" + searchToDate
                        + "&page=" + pageNumber;
                cart.addItemToCart(id);
                session.setAttribute("CART", cart);
            }

        } catch (SQLException ex) {
            LOGGER.fatal(ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.fatal(ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
