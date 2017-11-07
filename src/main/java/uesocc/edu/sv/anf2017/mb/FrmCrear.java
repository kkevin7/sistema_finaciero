/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DualListModel;
import uesocc.edu.sv.anf2017.ejb.CuentasFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;
import uesocc.edu.sv.anf2017.entities.Movimientos;

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

    @EJB
    private CuentasFacadeLocal cuent;
    private DualListModel<Cuentas> cuentas;
    private List<Movimientos> mov;
    
//    @PostConstruct
//    public void init() {
//       
//        List<Cuentas> Esta = cuent.findAll();
//        List<Cuentas> Esta2 = new ArrayList<>();
////        List<Cuentas> Source = cuent.findAll().subList(0, 5);
////        List<Cuentas> Target = new ArrayList<>();
//        
//        cuentas = new DualListModel<>(Esta, Esta2);
//    }

    public CuentasFacadeLocal getCuent() {
        return cuent;
    }

    public void setCuent(CuentasFacadeLocal cuent) {
        this.cuent = cuent;
    }

    public DualListModel<Cuentas> getCuentas() {
        return cuentas;
    }

    public void setCuentas(DualListModel<Cuentas> cuentas) {
        this.cuentas = cuentas;
    }

}
