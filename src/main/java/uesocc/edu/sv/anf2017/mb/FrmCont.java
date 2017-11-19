/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import uesocc.edu.sv.anf2017.ejb.CuentasFacadeLocal;
import uesocc.edu.sv.anf2017.ejb.EmpresasFacadeLocal;
import uesocc.edu.sv.anf2017.ejb.MovimientosFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;
import uesocc.edu.sv.anf2017.entities.Empresas;
import uesocc.edu.sv.anf2017.entities.Movimientos;
import uesocc.edu.sv.anf2017.entities.TipoCuenta;

/**
 *
 * @author yovany
 */
@Named(value = "frmCont")
@ViewScoped
public class FrmCont implements Serializable {

    public FrmCont() {

    }

    @EJB
    private EmpresasFacadeLocal efl;
    private Empresas emp;
    private boolean add;

    @EJB
    private MovimientosFacadeLocal mfl;
    private Movimientos movimiento;
    private List<Movimientos> movimientos;
    private List<Movimientos> movimientosxcuent = new ArrayList<>();
    private Movimientos movi;

    @EJB
    private CuentasFacadeLocal fl;
    private List<Cuentas> cuentas = new ArrayList<>();
    private String title;
    private Cuentas cuenta;
    private TipoCuenta tipo = new TipoCuenta();
    
    @PostConstruct
    public void init() {
        movi = new Movimientos();
        movimientos = resumen();
        movimientosxcuent = Collections.EMPTY_LIST;
        add = false;
    }

    public List<Cuentas> findCuentas(int codigo, String titulo) {
        setCuentas(Collections.EMPTY_LIST);
        try {
            setCuentas(getFl().findBy("idCuenta", String.valueOf(codigo)));
            setTitle(titulo);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return getCuentas();
    }

    public void crearMovimiento() {
        try {
            java.util.Date fecha = new java.util.Date();
            movi.setIdCuenta(cuenta);
            movi.setIdEmpresa(efl.find(1));
            movi.setFecha(fecha);
            if (movi != null && this.mfl != null) {
                this.mfl.create(movi);
                resumen();
                moviminetosPorCuenta();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<Movimientos> resumen() {
        movimientos = Collections.EMPTY_LIST;
        try {
            if (mfl != null) {
                java.util.Date fecha = new java.util.Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                movimientos = mfl.findBy("fecha", format.format(fecha));
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return movimientos;
    }

    public List<Movimientos> moviminetosPorCuenta() {
        movimientosxcuent = Collections.EMPTY_LIST;
        try {
            if (mfl != null) {
                movimientosxcuent = mfl.findByJoined("idCuenta", cuenta);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return movimientosxcuent;
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            if (movimiento != null && mfl != null) {
                movimiento.setMonto((Double) event.getNewValue());
                mfl.edit(movimiento);
                resumen();
                moviminetosPorCuenta();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void changeSelected(SelectEvent se) {
        try {
            if (se.getObject() != null) {
                this.movimiento = (Movimientos) se.getObject();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void changeSelectedCuenta(SelectEvent se) {
        try {
            if (se.getObject() != null) {
                this.cuenta = (Cuentas) se.getObject();
                moviminetosPorCuenta();
                add = true;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    //----- Getter and Setter Methods -----//
    public CuentasFacadeLocal getFl() {
        return fl;
    }

    public void setFl(CuentasFacadeLocal fl) {
        this.fl = fl;
    }

    public List<Cuentas> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuentas> cuentas) {
        this.cuentas = cuentas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Cuentas getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuentas cuenta) {
        this.cuenta = cuenta;
    }

    public MovimientosFacadeLocal getMfl() {
        return mfl;
    }

    public void setMfl(MovimientosFacadeLocal mfl) {
        this.mfl = mfl;
    }

    public Movimientos getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimientos movimiento) {
        this.movimiento = movimiento;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public List<Movimientos> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimientos> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Movimientos> getMovimientosxcuent() {
        return movimientosxcuent;
    }

    public void setMovimientosxcuent(List<Movimientos> movimientosxcuent) {
        this.movimientosxcuent = movimientosxcuent;
    }

    public EmpresasFacadeLocal getEfl() {
        return efl;
    }

    public void setEfl(EmpresasFacadeLocal efl) {
        this.efl = efl;
    }

    public Empresas getEmp() {
        return emp;
    }

    public void setEmp(Empresas emp) {
        this.emp = emp;
    }

    public Movimientos getMovi() {
        return movi;
    }

    public void setMovi(Movimientos movi) {
        this.movi = movi;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

}
