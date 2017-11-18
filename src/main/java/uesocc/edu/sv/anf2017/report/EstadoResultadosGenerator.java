/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.report;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import uesocc.edu.sv.anf2017.ejb.MovimientosFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;
import uesocc.edu.sv.anf2017.entities.Movimientos;

/**
 *
 * @author yovany
 */
@WebServlet(name = "EstadoResultadosGenerator", urlPatterns = {"/EstadoResultadosGenerator"})
public class EstadoResultadosGenerator extends HttpServlet implements Serializable {

    @Resource(lookup = "jndi_anf")
    DataSource source_jdbc;
    Connection conexion;

    @EJB
    private MovimientosFacadeLocal mfl;
    private List<Movimientos> movi;
    private Movimientos mv;

    
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
        response.setContentType("application/pdf");
        response.setHeader("Cache-Control", "max-age=0");

        try {
            conexion = null;

            File jasper = new File(request.getServletContext().getRealPath("/resources/reports/Estado.jasper"));
            HashMap parametros = new HashMap<>();

            try {
                if (source_jdbc != null) {
                    conexion = source_jdbc.getConnection();

                }

                JasperPrint impresor = JasperFillManager.fillReport(jasper.getPath(), parametros, conexion);
                JRPdfExporter exportador = new JRPdfExporter();
                exportador.setExporterInput(new SimpleExporterInput(impresor));
                OutputStreamExporterOutput salida = new SimpleOutputStreamExporterOutput(response.getOutputStream());
                exportador.setExporterOutput(salida);
                SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
                exportador.setConfiguration(conf);
                exportador.exportReport();
            } catch (IOException | SQLException | JRException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            } finally {
                if (conexion != null) {
                    conexion.close();
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
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
