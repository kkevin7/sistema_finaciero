/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import uesocc.edu.sv.anf2017.ejb.TipoCuentaFacadeLocal;
import uesocc.edu.sv.anf2017.entities.TipoCuenta;

/**
 *
 * @author yovany
 */
@Named(value = "frmTipoCuenta")
@ViewScoped
public class FrmTipoCuenta implements Serializable{

    /**
     * Creates a new instance of frmTipoCuenta
     */
    public FrmTipoCuenta() {
    }

    @EJB
    private TipoCuentaFacadeLocal tipofl;
    private TipoCuenta tipo;
    private List<TipoCuenta> lista;

    public List<TipoCuenta> find() {
        lista = new ArrayList<>();
        try {
            lista = tipofl.findAll();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return lista;
    }

    public TipoCuentaFacadeLocal getTipofl() {
        return tipofl;
    }

    public void setTipofl(TipoCuentaFacadeLocal tipofl) {
        this.tipofl = tipofl;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public List<TipoCuenta> getLista() {
        return lista;
    }

    public void setLista(List<TipoCuenta> lista) {
        this.lista = lista;
    }

}
