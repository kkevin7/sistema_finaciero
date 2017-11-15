/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author yovany
 */
@Named(value = "frmCrear")
@ViewScoped
public class FrmCrear implements Serializable {

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public FrmCrear() {
    }
    @Resource(lookup = "jndi_anf")
    private DataSource source_jdbc;
    private Connection conexion;

    public Connection jdbc() {
        conexion = null;
        try {
            conexion = source_jdbc.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return conexion;
    }

    public void estadoResultados() throws Exception {
        conexion = null;

        try {
            conexion = source_jdbc.getConnection();
            Map<String, Object> parametros = new HashMap<>();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/report1.jasper"));
            String fileName = jasper.getPath();
            JasperPrint jp = JasperFillManager.fillReport(fileName, parametros, conexion);
            System.out.println(jp);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            try (ServletOutputStream outStream = response.getOutputStream()) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jp, byteArrayOutputStream);
                response.setContentType("application/pdf");
                //response.setHeader("Content-Disposition", "attachment; filename=\"esta.pdf\"");
                outStream.write(byteArrayOutputStream.toByteArray());
                response.setHeader("Cache-Control", "max-age=0");
                response.setHeader(fileName, fileName);
                response.setContentLength(byteArrayOutputStream.toByteArray().length);
                outStream.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
            
        } catch (IOException | SQLException | JRException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

    }

    public void Estado() {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            InputStream reporte = getClass().getResourceAsStream("/sistema_financiero/src/main/webapp/resources/reports/EstadoResultados.jasper");
            Map<String, Object> parametros = new HashMap<>();
            response.setContentType("application/pdf");
            
            JasperPrint impresor = JasperFillManager.fillReport(reporte, parametros, jdbc());
            JRPdfExporter exportador = new JRPdfExporter();
            exportador.setExporterInput(new SimpleExporterInput(impresor));
            OutputStreamExporterOutput salida = new SimpleOutputStreamExporterOutput(response.getOutputStream());
            exportador.setExporterOutput(salida);
            SimplePdfExporterConfiguration conf = new SimplePdfExporterConfiguration();
            exportador.setConfiguration(conf);
            exportador.exportReport();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public DataSource getSource_jdbc() {
        return source_jdbc;
    }

    public void setSource_jdbc(DataSource source_jdbc) {
        this.source_jdbc = source_jdbc;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    

}
