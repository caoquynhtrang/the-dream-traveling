/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trangcq.booking.BookingDAO;
import trangcq.booking.BookingDTO;
import trangcq.bookingitem.BookingItemDAO;
import trangcq.bookingitem.BookingItemDTO;
import trangcq.cart.CartObject;
import trangcq.discount.DiscountDTO;
import trangcq.registration.RegistrationDTO;
import trangcq.traveltour.TravelTourDAO;
import trangcq.traveltour.TravelTourDTO;

/**
 *
 * @author USER
 */
@WebServlet(name = "ConfirmServlet", urlPatterns = {"/ConfirmServlet"})
public class ConfirmServlet extends HttpServlet {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ConfirmServlet.class);
    private final String VIEW_CART = "view.jsp";
    private final String CONFIRM_CART = "confirm.jsp";

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
        String url = VIEW_CART;
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                RegistrationDTO userDTO = (RegistrationDTO) session.getAttribute("USER");
                DiscountDTO discountDTO = cart.getDiscountDTO();
                if (cart != null) {
                    Map<Integer, Integer> items = cart.getItems();
                    Map<Integer, TravelTourDTO> travelItems = cart.getTravelTour();
                    BookingItemDAO bookingItemDAO = new BookingItemDAO();
                    BookingDAO bookingDAO = new BookingDAO();

                    List<String> confirmError = new ArrayList<>();
                    for (Integer tourId : items.keySet()) {
                        int totalBooked = bookingItemDAO.countTotalBookedTour(tourId);
                        TravelTourDTO tourInfo = travelItems.get(tourId);
                        int amount = items.get(tourId);
                        TravelTourDAO travelDAO = new TravelTourDAO();
                        int tourQuota = travelDAO.getTourQuota(tourId);
                        int notBookedYet = tourQuota - totalBooked;

                        if (notBookedYet - amount  <  0) {
                            confirmError.add("Tour: " + tourInfo.getTourName() + " is invalid! (Remainings: " + notBookedYet + "!)");
                        }
                    }

                    int discount = -1;
                    if (discountDTO != null) {
                        discount = discountDTO.getId();
                        boolean checkDiscount = bookingDAO.checkDiscount(discount, userDTO.getId());
                        if (!checkDiscount) {
                            confirmError.add("Discount is invalid");
                        }
                    }
                    if (confirmError.isEmpty()) {
                        BookingDTO bookingDTO = new BookingDTO(userDTO.getId(), discount, new Timestamp(System.currentTimeMillis()));

                        if (items != null) {
                            int idBookingInsert = bookingDAO.insertBookingTour(bookingDTO);
                            bookingDTO.setId(idBookingInsert);
                            if (idBookingInsert != -1) {
                                Set<Integer> keyList = items.keySet();
                                for (Integer tourId : keyList) {
                                    BookingItemDTO bookingItemDTO = new BookingItemDTO(bookingDTO.getId(), tourId, items.get(tourId));
                                    bookingItemDAO.insertBookingItem(bookingItemDTO);
                                }
                                url = CONFIRM_CART;
                                request.setAttribute("CART", cart);
                                session.removeAttribute("CART");
                            }
                        }
                    } else {
                        request.setAttribute("CONFIRM_ERROR", confirmError);
                    }
                }
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
