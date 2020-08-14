/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trangcq.traveltour.TravelTourDAO;
import trangcq.traveltour.TravelTourDTO;

/**
 *
 * @author USER
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchServlet.class);
    private final String URL_SEARCH_PAGE = "search.jsp";

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
        String url = URL_SEARCH_PAGE;
        String searchLocation = request.getParameter("txtSearchLocation");
        String searchFromPrice = request.getParameter("txtFromPrice");
        String searchToPrice = request.getParameter("txtToPrice");
        String searchFromDate = request.getParameter("txtFromDate");
        String searchToDate = request.getParameter("txtToDate");
        String pageNumber = request.getParameter("page");
        try {
            if (searchLocation.isEmpty() && searchFromPrice.isEmpty() && searchToPrice.isEmpty() && searchFromDate.isEmpty() && searchToDate.isEmpty()) {

            } else {
                Date fromDate = null;
                try {
                    long time = ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(searchFromDate)).getTime();
                    fromDate = new Date(time);
                } catch (Exception e) {
                }
                Date toDate = null;
                try {
                    long time = ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(searchToDate)).getTime();
                    toDate = new Date(time);
                    if (fromDate != null && fromDate.compareTo(toDate) > 0) {
                        toDate = null;
                    }
                } catch (Exception e) {
                }
                float fromPrice = -1;
                try {
                    fromPrice = Float.parseFloat(searchFromPrice);
                } catch (Exception e) {
                }

                float toPrice = -1;
                try {
                    toPrice = Float.parseFloat(searchToPrice);
                } catch (Exception e) {

                }

                TravelTourDAO dao = new TravelTourDAO();
                List<TravelTourDTO> listTravelTour;
                int pageIndex = 1;
                try {
                    pageIndex = Integer.parseInt(pageNumber);
                } catch (Exception e) {
                }
                if (fromPrice == -1 && toPrice == -1 && searchLocation.isEmpty() && fromDate == null && toDate == null) {
                    //do nothing
                } else {
                    if (pageNumber == null) {
                        listTravelTour = dao.searchTourPaging(searchLocation, fromDate, toDate, toPrice, fromPrice);
                    } else {
                        listTravelTour = dao.searchTourPaging(searchLocation, fromDate, toDate, toPrice, fromPrice, pageIndex);
                    }
                    int totalTravelTour = dao.countTotalTravelTour(searchLocation, fromDate, toDate, toPrice, fromPrice);
                    int page = (int) Math.ceil(totalTravelTour / 20);
                    int[] pageArr = new int[page];
                    request.setAttribute("SEARCH_RESULT", listTravelTour);
                    request.setAttribute("TOTAL_PAGE", pageArr);
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
