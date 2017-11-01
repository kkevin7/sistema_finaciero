/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import uesocc.edu.sv.anf2017.ejb.CuentasFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;

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
    private CuentasFacadeLocal fl;
    private List<Cuentas> cuent = new ArrayList<>();
    private String title;
    private Cuentas cuenta;
    
    /**
     * Metodo para devolver una lista con todas las cuentas de tipo Activo
     * Corriente :V
     *
     * @return
     */
    public List<Cuentas> findActivosCorrientes() {
        setCuent(Collections.EMPTY_LIST);
        try {
            setCuent(getFl().findBy("idCuenta", "11"));
            setTitle("Activos Corrientes");
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return getCuent();
    }

    /**
     * Metodo para devolver una lista con todas las cuentas de tipo Activo No
     * Corriente :V
     *
     * @return
     */
    public List<Cuentas> findActivosNoCorrientes() {
        setCuent(Collections.EMPTY_LIST);
        try {
            setCuent(getFl().findBy("idCuenta", "12"));
            setTitle("Activos No Corrientes");
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return getCuent();
    }

    /**
     *
     */
    public void modificar() {
        try {
            if (cuenta != null && this.fl != null) {
                this.fl.edit(cuenta);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Metodo que se ejecuta luego de dar enter al estar editando la tabla
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {
        System.out.println(event.getRowKey());
        cuenta.setDescripcion((String)event.getNewValue());
        modificar();
    }

    
    public void changeSelected(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.cuenta = (Cuentas) se.getObject();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public CuentasFacadeLocal getFl() {
        return fl;
    }

    public void setFl(CuentasFacadeLocal fl) {
        this.fl = fl;
    }

    public List<Cuentas> getCuent() {
        return cuent;
    }

    public void setCuent(List<Cuentas> cuent) {
        this.cuent = cuent;
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
}
