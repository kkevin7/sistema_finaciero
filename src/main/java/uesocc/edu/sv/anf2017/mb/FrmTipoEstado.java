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
import uesocc.edu.sv.anf2017.ejb.TipoEstadoFacadeLocal;
import uesocc.edu.sv.anf2017.entities.TipoEstado;

/**
 *
 * @author yovany
 */
@Named(value = "frmTipoEstado")
@ViewScoped
public class FrmTipoEstado implements Serializable{

    /**
     * Creates a new instance of frmTipoCuenta
     */
    public FrmTipoEstado() {
    }

    @EJB
    private TipoEstadoFacadeLocal tipofl;
    private TipoEstado tipo;
    private List<TipoEstado> lista;

    public List<TipoEstado> find() {
        lista = new ArrayList<>();
        try {
            lista = tipofl.findAll();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return lista;
    }

    public TipoEstadoFacadeLocal getTipofl() {
        return tipofl;
    }

    public void setTipofl(TipoEstadoFacadeLocal tipofl) {
        this.tipofl = tipofl;
    }

    public TipoEstado getTipo() {
        return tipo;
    }

    public void setTipo(TipoEstado tipo) {
        this.tipo = tipo;
    }

    public List<TipoEstado> getLista() {
        return lista;
    }

    public void setLista(List<TipoEstado> lista) {
        this.lista = lista;
    }

}
