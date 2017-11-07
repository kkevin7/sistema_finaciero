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
import javax.faces.event.ValueChangeEvent;
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
    private MovimientosFacadeLocal mfl;
    private Movimientos mov = new Movimientos();
    private int tipoEstado;
    private Integer tipoCuenta;

    @EJB
    private EmpresasFacadeLocal efl;
    private Empresas emp = new Empresas();

    @EJB
    private CuentasFacadeLocal fl;
    private List<Cuentas> cuent = new ArrayList<>();
    private List<Cuentas> activos = new ArrayList<>();
    private List<Cuentas> pasivos = new ArrayList<>();
    private List<Cuentas> capital = new ArrayList<>();
    private String title;
    private Cuentas cuenta;
    private TipoCuenta tipo = new TipoCuenta();

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

    public List<Cuentas> findBy(int codigo, String titulo) {
        setCuent(Collections.EMPTY_LIST);
        try {
            setCuent(getFl().findBy("idCuenta", String.valueOf(codigo)));
            setTitle(titulo);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        System.out.println(fl.find(12));
        return getCuent();
    }

    public void option() {
        switch (tipoCuenta) {
            case 1:
                findBy(tipoCuenta, "Activos");
                break;
            case 2:
                findBy(tipoCuenta, "Pasivos");
                break;
            case 3:
                findBy(tipoCuenta, "Capital");
                break;
            case 4:
                findBy(tipoCuenta, "Gastos");
                break;
            case 5:
                findBy(tipoCuenta, "Ingresos");
                break;
            default:
                break;
        }
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
     *
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {
        System.out.println(event.getRowKey());
        cuenta.setDescripcion((String) event.getNewValue());
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

    public List<Cuentas> complete(String query) {
        List<Cuentas> all = fl.findAll();
        List<Cuentas> filter = new ArrayList<>();

        for (int i = 0; i < all.size(); i++) {
            Cuentas skin = all.get(i);
            if (skin.getNombre().toLowerCase().startsWith(query)) {
                filter.add(skin);
            }
        }
        return filter;
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

    public int getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(int tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public Integer getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(Integer tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public List<Cuentas> getActivos() {
        return activos;
    }

    public void setActivos(List<Cuentas> activos) {
        this.activos = activos;
    }

    public List<Cuentas> getPasivos() {
        return pasivos;
    }

    public void setPasivos(List<Cuentas> pasivos) {
        this.pasivos = pasivos;
    }

    public List<Cuentas> getCapital() {
        return capital;
    }

    public void setCapital(List<Cuentas> capital) {
        this.capital = capital;
    }

}
