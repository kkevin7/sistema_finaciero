package uesocc.edu.sv.anf2017.mb;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
import uesocc.edu.sv.anf2017.ejb.MovimientosFacadeLocal;
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
    Double ventas, rebVentas, invInicial, compras, gasCompras, devCompras, invFinal, gasAdm, gasOp, gasFinan, gasVent, otrosGas, otrosIng, impuesto, reserva, uBruta, uOPe, UAI, utilidad;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private List<Movimientos> datos = new ArrayList<Movimientos>();
    Map<String, Object> parametros = new HashMap<String, Object>();

    public void estadoResultados() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("uesocc.edu.sv_anf2017_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        Query v = em.createNamedQuery("Movimientos.ventas");
        ventas = (Double) v.getSingleResult();
        Query rv = em.createNamedQuery("Movimientos.rebVentas");
        rebVentas = (Double) rv.getSingleResult();
        Query iI = em.createNamedQuery("Movimientos.inventarioIni");
        invInicial = (Double) iI.getSingleResult();
        Query c = em.createNamedQuery("Movimientos.compras");
        compras = (Double) c.getSingleResult();
        Query gc = em.createNamedQuery("Movimientos.gastosCompras");
        gasCompras = (Double) gc.getSingleResult();
        Query dc = em.createNamedQuery("Movimientos.rebCompras");
        devCompras = (Double) dc.getSingleResult();
        Query iF = em.createNamedQuery("Movimientos.inventarioFin");
        invFinal = (Double) iF.getSingleResult();
        System.out.println(invFinal);
        uBruta = ventas - rebVentas - (invInicial + compras + gasCompras - devCompras - invFinal);
        Query go = em.createNamedQuery("Movimientos.gastoOperativo");
        gasOp = (Double) go.getSingleResult();
        Query ga = em.createNamedQuery("Movimientos.gastoAdm");
        gasAdm = (Double) ga.getSingleResult();
        Query gv = em.createNamedQuery("Movimientos.gastoVentas");
        gasVent = (Double) gv.getSingleResult();
        Query gf = em.createNamedQuery("Movimientos.gastoFinanciero");
        gasFinan = (Double) gf.getSingleResult();
        uOPe = uBruta - gasAdm - gasFinan - gasOp - gasVent;
        Query og = em.createNamedQuery("Movimientos.otrosGastos");
        otrosGas = (Double) og.getSingleResult();
        Query oI = em.createNamedQuery("Movimientos.otrosIngresos");
        otrosIng = (Double) oI.getSingleResult();
        UAI = uOPe - otrosGas + otrosIng;
        if (UAI >= 150000) {
            impuesto = UAI * 0.3;
        } else {
            impuesto = UAI * 0.25;

        }
        reserva = UAI * 0.07;

        utilidad = UAI - impuesto - reserva;
    }

    public void putParametros() {
        parametros.put("Ventas", ventas);
        parametros.put("revVentas", rebVentas);
        parametros.put("invInicial", invInicial);
        parametros.put("compras", compras);
        parametros.put("gasCompras", gasCompras);
        parametros.put("devCompras", devCompras);
        parametros.put("invFinal", invFinal);
        parametros.put("uBruta", uBruta);
        parametros.put("gasOp", gasOp);
        parametros.put("gasAdm", gasAdm);
        parametros.put("gasVent", gasVent);
        parametros.put("gasFinan", gasFinan);
        parametros.put("uOPe", uOPe);
        parametros.put("otrosIng", otrosIng);
        parametros.put("otrosGas", otrosGas);
        parametros.put("UAI", UAI);
        parametros.put("impuesto", impuesto);
        parametros.put("reserva", reserva);
        parametros.put("utilidad", utilidad);
    }

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

            try {
                parametros.put("nom_empresa", nombreEmpresa);
                parametros.put("fechaInicio", limpiarUtilDate(fechaIncial));
                parametros.put("fechaFin", limpiarUtilDate(fechaFin));
                parametros.put("periodo", "Periodo realizado del " + limpiarUtilDate(fechaIncial) + " al " + limpiarUtilDate(fechaFin));
            
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }

        }
        if (tipoReporte.equals("ER")) {
            tipoJasper = "EstadoResultados.jasper";

            estadoResultados();
            putParametros();
        }

    }

    public void verPDF() throws Exception {

        try {
            parametros = new HashMap<String, Object>();
            tipoReportesJasper();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/" + tipoJasper));

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

        try {
            parametros = new HashMap<String, Object>();
            tipoReportesJasper();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/" + tipoJasper));

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

        try {
            parametros = new HashMap<String, Object>();
            tipoReportesJasper();
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reports/" + tipoJasper));

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
        cal.add(Calendar.DAY_OF_YEAR, 1);
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
