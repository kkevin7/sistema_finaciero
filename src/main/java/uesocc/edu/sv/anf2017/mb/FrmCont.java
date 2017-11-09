/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
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

    @EJB
    private MovimientosFacadeLocal mfl;
    private Movimientos mov = new Movimientos();
    private List<Movimientos> movimientos;
    private List<Movimientos> movimientosxcuent;

    @EJB
    private CuentasFacadeLocal fl;
    private List<Cuentas> cuentas = new ArrayList<>();
    private String title;
    private Cuentas cuenta;
    private TipoCuenta tipo = new TipoCuenta();

    @PostConstruct
    public void init() {
        movimientos = resumen();
    }

    public List<Cuentas> findBy(int codigo, String titulo) {
        setCuentas(Collections.EMPTY_LIST);
        try {
            setCuentas(getFl().findBy("idCuenta", String.valueOf(codigo)));
            setTitle(titulo);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return getCuentas();
    }

    public void modificar() {
        try {
            if (cuenta != null && this.fl != null) {
                this.fl.edit(cuenta);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

//    public void crearMovimiento() {
//        mov.setIdCuenta(cuenta);
//        mov.setIdEmpresa(empresas);
//        try {
//            if (mov != null && this.mfl != null) {
//                this.mfl.edit(mov);
//            }
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//        }
//    }
    public List<Movimientos> resumen() {
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        movimientos = mfl.findBy("fecha", format.format(fecha));
        return movimientos;
    }
    
    public List<Movimientos> moviminetosPorCuenta() {
        movimientosxcuent = mfl.findByJoined("idCuenta", cuenta);
        return movimientosxcuent;
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            if (mov != null && mfl != null) {
                mov.setMonto((BigDecimal) event.getNewValue());
                mfl.edit(mov);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void changeSelected(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.mov = (Movimientos) se.getObject();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
    
    public void changeSelectedCuenta(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.cuenta = (Cuentas) se.getObject();
                moviminetosPorCuenta();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        
    }

    public void onItemSelect(SelectEvent event) {
        System.out.println(cuenta);
    }

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

    public Movimientos getMov() {
        return mov;
    }

    public void setMov(Movimientos mov) {
        this.mov = mov;
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
        return movimientos;
    }

    public void setMovimientosxcuent(List<Movimientos> movimientosxcuent) {
        this.movimientosxcuent = movimientosxcuent;
    }

}
