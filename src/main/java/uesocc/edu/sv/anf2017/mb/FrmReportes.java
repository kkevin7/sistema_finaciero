package uesocc.edu.sv.anf2017.mb;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import net.sf.jasperreports.engine.JRParameter;
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
    Double actCorrientePosi, actCorriNegativo, activosCorrientes;
    Double actNOCorriPositivo, actNOCorriNegativo, activosNOCorrientes;
    Double pasCorrientePos, pasCorrienteNeg, pasivoCorriente;
    Double pasNoCorrientePos, pasNoCorrienteNeg, pasivoNOCorriente;
    Double capitalPos, capitalNeg, capital;
    Double totalActivos, cuentasCobrar, cuentasPagar;
    Double totalPasivosCapital, totalPasivos, inventarioBa;
    Double actOtrosPos, actOtrosNeg, activosOtros;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private List<Movimientos> datos = new ArrayList<Movimientos>();
    Map<String, Object> parametros = new HashMap<String, Object>();

    public Double obtenerValidar(Query query) {
        Double variable = 0.0;
        try {
            variable = (Double) query.getSingleResult();
        } catch (Exception e) {
        }
        if (variable == null) {
            variable = 0.0;
        }
        return variable;
    }

    public void estadoResultados() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("uesocc.edu.sv_anf2017_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        Query v = em.createNamedQuery("Movimientos.ventas");
        ventas = obtenerValidar(v);
        Query rv = em.createNamedQuery("Movimientos.rebVentas");
        rebVentas = obtenerValidar(rv);
        Query iI = em.createNamedQuery("Movimientos.inventarioIni");
        invInicial = obtenerValidar(iI);
        Query c = em.createNamedQuery("Movimientos.compras");
        compras = obtenerValidar(c);
        Query gc = em.createNamedQuery("Movimientos.gastosCompras");
        gasCompras = obtenerValidar(gc);
        Query dc = em.createNamedQuery("Movimientos.rebCompras");
        devCompras = obtenerValidar(dc);
        Query iF = em.createNamedQuery("Movimientos.inventarioFin");
        invFinal = obtenerValidar(iF);
        uBruta = ventas - rebVentas - (invInicial + compras + gasCompras - devCompras - invFinal);
        Query go = em.createNamedQuery("Movimientos.gastoOperativo");
        gasOp = obtenerValidar(go);
        Query ga = em.createNamedQuery("Movimientos.gastoAdm");
        gasAdm = obtenerValidar(ga);
        Query gv = em.createNamedQuery("Movimientos.gastoVentas");
        gasVent = obtenerValidar(gv);
        Query gf = em.createNamedQuery("Movimientos.gastoFinanciero");
        gasFinan = obtenerValidar(gf);
        uOPe = uBruta - gasAdm - gasFinan - gasOp - gasVent;
        Query og = em.createNamedQuery("Movimientos.otrosGastos");
        otrosGas = obtenerValidar(og);
        Query oI = em.createNamedQuery("Movimientos.otrosIngresos");
        otrosIng = obtenerValidar(oI);
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

    public void balanceGeneral() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("uesocc.edu.sv_anf2017_war_1.0-SNAPSHOTPU");
            EntityManager em = emf.createEntityManager();
            Query ACPositivo = em.createNamedQuery("Movimientos.activosCorrientePOSI");
            actCorrientePosi = obtenerValidar(ACPositivo);
            Query ACNegativo = em.createNamedQuery("Movimientos.activosCorrienteNEG");
            actCorriNegativo = obtenerValidar(ACNegativo);
            //Activos Corrientes
            activosCorrientes = actCorrientePosi - actCorriNegativo;
            System.out.println("Activos corrientes:::---> " + activosCorrientes);
            Query ACNOPositivo = em.createNamedQuery("Movimientos.activosNOCorrientesPOSI");
            actNOCorriPositivo = obtenerValidar(ACNOPositivo);
            Query ACNONegativo = em.createNamedQuery("Movimientos.activosNOCorrientesNEG");
            actNOCorriNegativo = obtenerValidar(ACNONegativo);
            //Activos No Corrientes
            activosNOCorrientes = actNOCorriPositivo - actNOCorriNegativo;
            System.out.println("Activos No corrientes:::--->" + activosNOCorrientes);
            Query PACPositivo = em.createNamedQuery("Movimientos.pasivosCorrientesPOSI");
            pasCorrientePos = obtenerValidar(PACPositivo);
            Query PACNegativo = em.createNamedQuery("Movimientos.pasivosCorrientesNEG");
            pasCorrienteNeg = obtenerValidar(PACNegativo);
            //Activos Otros
            Query AOPositivo = em.createNamedQuery("Movimientos.activosOtrosPOSI");
            actOtrosPos = obtenerValidar(AOPositivo);
            Query AONegativo = em.createNamedQuery("Movimientos.activosOtrosNEG");
            actOtrosNeg = obtenerValidar(AONegativo);
            activosOtros = actOtrosPos - actOtrosNeg;
            System.out.println("Activos Otros:::--->" + activosOtros);
            
            //Pasivos Corrientes
            pasivoCorriente = pasCorrientePos - pasCorrienteNeg;
            System.out.println("Pasivos Corriente:::--->" + pasivoCorriente);
            Query PACNOPositivo = em.createNamedQuery("Movimientos.pasivosNOCorrientesPOSI");
            pasNoCorrientePos = obtenerValidar(PACNOPositivo);
            Query PACNONegativo = em.createNamedQuery("Movimientos.pasivosNOCorrientesNEG");
            pasNoCorrienteNeg = obtenerValidar(PACNONegativo);
            //Pasivos No Corrientes
            pasivoNOCorriente = pasNoCorrientePos - pasNoCorrienteNeg;
            System.out.println("Pasivos NO Corriente:::--->" + pasivoNOCorriente);
            Query capPositivo = em.createNamedQuery("Movimientos.capitalPOSI");
            capitalPos = obtenerValidar(capPositivo);
            Query capNegativo = em.createNamedQuery("Movimientos.capitalNEG");
            capitalNeg = obtenerValidar(capNegativo);
            
            
            if (tipoReporte.equals("BG")) {
                reserva=0.0;
                utilidad=0.0;
            }
            
            
            //Capital
            capital = capitalPos - capitalNeg + reserva + utilidad;
            System.out.println("Capital:::----> " + capital);
            
            
            
            //Total Suma Activos
            totalActivos = activosCorrientes + activosNOCorrientes + activosOtros;
            System.out.println("Total de Activos::---> " + totalActivos);
            //Total Pasivo + Capital (falta Utilidad y reserva)
            totalPasivosCapital = pasivoCorriente + pasivoNOCorriente + capital ;
            totalPasivos = pasivoCorriente + pasivoNOCorriente;
            System.out.println("Total de Capita y Pasivos::--> " + totalPasivosCapital);

            Query cc = em.createNamedQuery("Movimientos.cuentasCobrar");
            cuentasCobrar = obtenerValidar(cc);
            Query cp = em.createNamedQuery("Movimientos.cuentasPagar");
            cuentasPagar = obtenerValidar(cp);
            Query ib= em.createNamedQuery("Movimientos.inventarioB");
            inventarioBa = obtenerValidar(ib);
            

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void parametrosRazones() {
        
        parametros.put("pruebaAcida", ((activosCorrientes - inventarioBa) / pasivoCorriente));
        parametros.put("capTrabajo", new BigDecimal(activosCorrientes - pasivoCorriente).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("razonCorriente", new BigDecimal(activosCorrientes / pasivoCorriente).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("ppPago", new BigDecimal(cuentasPagar / (compras / 360)).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("ppCobro", new BigDecimal(cuentasCobrar / (ventas / 360)).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("rotAct", new BigDecimal(ventas / totalActivos).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("indEndeudamiento", new BigDecimal(totalPasivos / totalActivos).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("margenBruto", new BigDecimal((uBruta / ventas) * 100).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("uOpe", new BigDecimal((uOPe / ventas) * 100).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("uNeta", new BigDecimal((utilidad / ventas) * 100).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("RSA", new BigDecimal((utilidad / totalActivos) * 100).setScale(4, RoundingMode.HALF_UP).doubleValue());
        parametros.put("RSP", new BigDecimal((utilidad / (totalActivos - totalPasivos)) * 100).setScale(4, RoundingMode.HALF_UP).doubleValue());

    }

    public void parametrosBalance() {
        parametros.put("actCorrientes", activosCorrientes.toString());
        parametros.put("actNoCorrientes", activosNOCorrientes.toString());
        parametros.put("pasCorrientes", pasivoCorriente.toString());
        parametros.put("pasNoCorrientes", pasivoNOCorriente.toString());
        parametros.put("capital", capital.toString());
        parametros.put("totalActivos", totalActivos.toString());
        parametros.put("totalPasCapital", totalPasivosCapital.toString());
        parametros.put("reserva", reserva.toString());
        parametros.put("utilidad", utilidad.toString());
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

        parametros.put(JRParameter.REPORT_MAX_COUNT, 1);

        if (tipoReporte.equals("BG")) {
            tipoJasper = "balanceGeneral.jasper";

            try {
                parametros.put("nom_empresa", nombreEmpresa);
                parametros.put("fechaInicio", limpiarUtilDate(fechaIncial));
                parametros.put("fechaFin", limpiarUtilDate(fechaFin));
                parametros.put("periodo", "Periodo realizado del " + limpiarUtilDate(fechaIncial) + " al " + limpiarUtilDate(fechaFin));
                balanceGeneral();
                parametrosBalance();

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }

        }
        if (tipoReporte.equals("ER")) {
            tipoJasper = "EstadoResultados.jasper";
            try {
                parametros.put("nom_empresa", nombreEmpresa);

                parametros.put("periodo", "Periodo realizado del " + limpiarUtilDate(fechaIncial) + " al " + limpiarUtilDate(fechaFin));

                estadoResultados();
                putParametros();

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        if (tipoReporte.equals("RZ")) {
            tipoJasper = "ratios.jasper";
            try {
                parametros.put("nom_empresa", nombreEmpresa);

                parametros.put("periodo", "Periodo realizado del " + limpiarUtilDate(fechaIncial) + " al " + limpiarUtilDate(fechaFin));

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            estadoResultados();
            balanceGeneral();
            putParametros();
            parametrosRazones();
        }
        if (tipoReporte.equals("BGU")) {
            tipoJasper = "balanceGeneralUtilidad.jasper";

            try {
                parametros.put("nom_empresa", nombreEmpresa);
                parametros.put("fechaInicio", limpiarUtilDate(fechaIncial));
                parametros.put("fechaFin", limpiarUtilDate(fechaFin));
                parametros.put("periodo", "Periodo realizado del " + limpiarUtilDate(fechaIncial) + " al " + limpiarUtilDate(fechaFin));
                estadoResultados();
                balanceGeneral();
                parametrosBalance();

            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }

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
