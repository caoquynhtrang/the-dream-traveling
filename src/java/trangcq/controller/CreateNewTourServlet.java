/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import trangcq.traveltour.TravelTourDAO;
import trangcq.traveltour.TravelTourDTO;
import trangcq.traveltour.TravelTourInsertErr;
import trangcq.utilities.Util;

/**
 *
 * @author USER
 */
@WebServlet(name = "CreateNewTourServlet", urlPatterns = {"/CreateNewTourServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CreateNewTourServlet extends HttpServlet {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CreateNewTourServlet.class);
    private final String CREATE_NEW_TOUR = "createTour.jsp";

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
        String url = CREATE_NEW_TOUR;

        TravelTourInsertErr errors = new TravelTourInsertErr();
        boolean fourdErr = false;
        try {
            boolean isMutilpart = ServletFileUpload.isMultipartContent(request);

            if (isMutilpart) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);

                List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));

                Map<String, String> parameters = new HashMap<>();
                FileItem fileItem = null;
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        parameters.put(item.getFieldName(), item.getString());
                    }
                    if (item.getFieldName().equals("fileImage")) {
                        fileItem = item;
                    }
                }
                String tourName = parameters.get("txtTourName");
                String place = parameters.get("txtPlace");
                String txtPrice = parameters.get("txtPrice");
                String txtFromDate = parameters.get("txtFromDate");
                String txtToDate = parameters.get("txtToDate");
                String txtQuota = parameters.get("txtQuota");
                String imageLink = null;

                try {
                    String uploadPath = request.getServletContext().getRealPath("/") + "image-test" + File.separator;
                    String fileName = Util.randomFileName(25);
                    String extension = Util.getFileExtension(fileItem.getName());
                    File fileUpload = new File(uploadPath + fileName + extension);
                    fileItem.write(fileUpload);
                    imageLink = "image-test/" + fileName + extension;
                } catch (Exception ex) {
                    errors.setImageErr("Failed to upload image!");
                }

                if (tourName.trim().length() < 2 || tourName.trim().length() > 70) {
                    fourdErr = true;
                    errors.setTourNameErr("Tour name string is requred from 2 to 70 characters");
                }
                float price = 0;
                try {
                    price = Float.parseFloat(txtPrice);
                } catch (Exception e) {

                }
                if (price == 0) {
                    fourdErr = true;
                    errors.setPriceErr("Input price must be interger");
                }

                if (place.trim().length() < 2 || place.trim().length() > 70) {
                    fourdErr = true;
                    errors.setPlaceErr("Place string is requred from 2 to 70 characters");
                }

                Date fromDate = null;
                try {
                    long time = ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(txtFromDate)).getTime();
                    fromDate = new Date(time);
                } catch (ParseException ex) {
                }

                if (fromDate == null) {
                    fourdErr = true;
                    errors.setFromDateErr("Date must be format");
                }

                Date toDate = null;
                try {
                    long time = ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(txtToDate)).getTime();
                    toDate = new Date(time);
                } catch (ParseException ex) {
                }
                if (toDate == null) {
                    fourdErr = true;
                    errors.setToDateErr("Date must be format");
                }

                if (fromDate != null && toDate.compareTo(fromDate) < 0) {
                    fourdErr = true;
                    errors.setToDateGreaterErr("To date must be greater than or equal with from date.");
                }

                int quota = 0;
                try {
                    quota = Integer.parseInt(txtQuota);
                } catch (Exception e) {

                }
                if (quota == 0) {
                    fourdErr = true;
                    errors.setQuotaErr("Input quota must be interger");
                }
                if (fourdErr) {
                    request.setAttribute("CREATEERROR", errors);
                } else {
                    TravelTourDAO dao = new TravelTourDAO();
                    TravelTourDTO dto = new TravelTourDTO(place, tourName, imageLink, quota, price, fromDate, toDate);
                    boolean result = dao.insertTourTravel(dto);
                    if (result) {
                        url = "search.jsp";
                    }
                }
            }

        } catch (SQLException ex) {
            LOGGER.fatal(ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.fatal(ex.getMessage());
        } catch (FileUploadException ex) {
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
