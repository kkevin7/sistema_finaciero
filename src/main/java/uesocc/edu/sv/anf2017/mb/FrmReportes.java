package uesocc.edu.sv.anf2017.mb;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import uesocc.edu.sv.anf2017.ejb.EmpresasFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Empresas;
import uesocc.edu.sv.anf2017.entities.Movimientos;

@Named(value = "frmReportes")
@ViewScoped
public class FrmReportes implements Serializable {

    @EJB
    private EmpresasFacadeLocal ejbEmpresas;
    private Empresas empresas = new Empresas();

    @Resource(lookup = "jndi_anf")
    DataSource dbFinanciera;

    Calendar cal = Calendar.getInstance();
    String nombreEmpresa;
    Date fechaFin;
    Date fechaIncial;
    String tipoReporte;
    String tipoJasper;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private List<Movimientos> datos = new ArrayList<Movimientos>();

    public FrmReportes() {
        try {
            fechaFin = cal.getTime();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Deprecated
    public List<Empresas> obtenerTodos() {
        List<Empresas> salida = new ArrayList();
        try {
            if (ejbEmpresas != null) {
                salida = ejbEmpresas.findAll();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return salida;
    }

    public List<Movimientos> getDatos() {

        try {
            Movimientos info = new Movimientos();
            info.setFecha(fechaFin);
            datos.add(info);

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return datos;
    }

    public void tipoReportesJasper() {
        if (tipoReporte.equals("BG")) {
            tipoJasper = "balanceGeneral.jasper";
        }
        if (tipoReporte.equals("ER")) {
            tipoJasper = "EstadosResultado.jasper";
        }

    }

    public void verPDF() throws Exception {
        tipoReportesJasper();

        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/" + tipoJasper));
            parametros.put("nom_empresa", nombreEmpresa);
            parametros.put("fechaInicio", limpiarUtilDate(fechaIncial));
            parametros.put("fechaFin", limpiarUtilDate(fechaFin));
            parametros.put("periodo", "Periodo realizado del "+limpiarUtilDate(fechaFin)+" al "+ limpiarUtilDate(fechaFin));

            Connection conexion = null;
            try {
                if (dbFinanciera != null) {
                    conexion = dbFinanciera.getConnection();
                }
                byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, conexion);

                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);

                outStream.flush();
                outStream.close();

                FacesContext.getCurrentInstance().responseComplete();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                    }
                }
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void exportarPDF() throws JRException, IOException {
        tipoReportesJasper();
        try {

            Map<String, Object> parametros = new HashMap<String, Object>();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/" + tipoJasper));
            parametros.put("nom_empresa", nombreEmpresa);
            parametros.put("fechaInicio", limpiarUtilDate(fechaIncial));
            parametros.put("fechaFin", limpiarUtilDate(fechaFin));
            parametros.put("perido", "Periodo realizado del "+limpiarUtilDate(fechaFin)+" al "+ limpiarUtilDate(fechaFin));

            Connection conexion = null;
            try {
                if (dbFinanciera != null) {
                    conexion = dbFinanciera.getConnection();
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, conexion);

                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=estados_financieros.pdf");
                ServletOutputStream stream = response.getOutputStream();

                JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

                stream.flush();
                stream.close();
                FacesContext.getCurrentInstance().responseComplete();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                    }
                }
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void exportarExcel() throws JRException, IOException {
        tipoReportesJasper();
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/" + tipoJasper));
            parametros.put("nom_empresa", nombreEmpresa);
            parametros.put("fechaInicio", limpiarUtilDate(fechaIncial));
            parametros.put("fechaFin", limpiarUtilDate(fechaFin));
            parametros.put("perido", "Periodo realizado del "+limpiarUtilDate(fechaFin)+" al "+ limpiarUtilDate(fechaFin));

            Connection conexion = null;
            try {
                if (dbFinanciera != null) {
                    conexion = dbFinanciera.getConnection();
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, conexion);

                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=estados_financieros.xlsx");
                ServletOutputStream stream = response.getOutputStream();

                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
                exporter.exportReport();

                stream.flush();
                stream.close();
                FacesContext.getCurrentInstance().responseComplete();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String limpiarUtilDate(Date fechaIngresada) throws ParseException {
        String fecha = fechaIngresada.toString();
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        Date date = (Date) formatter.parse(fecha);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String formatedDate = cal.get(Calendar.YEAR) + "/"
                + (cal.get(Calendar.MONTH) + 1)
                + "/" + cal.get(Calendar.DATE);

        return formatedDate;
    }

    public EmpresasFacadeLocal getEjbEmpresas() {
        return ejbEmpresas;
    }

    public void setEjbEmpresas(EmpresasFacadeLocal ejbEmpresas) {
        this.ejbEmpresas = ejbEmpresas;
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaIncial() {
        return fechaIncial;
    }

    public void setFechaIncial(Date fechaIncial) {
        this.fechaIncial = fechaIncial;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getTipoJasper() {
        return tipoJasper;
    }

    public void setTipoJasper(String tipoJasper) {
        this.tipoJasper = tipoJasper;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

}
