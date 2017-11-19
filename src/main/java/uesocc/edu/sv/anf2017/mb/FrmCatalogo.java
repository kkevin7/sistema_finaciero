/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import uesocc.edu.sv.anf2017.ejb.CuentasFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;

/**
 *
 * @author yovany
 */
@Named(value = "frmCatalogo")
@ViewScoped
public class FrmCatalogo implements Serializable {

    /**
     * Creates a new instance of frmCatalogo
     */
    public FrmCatalogo() {
    }

    @EJB
    private CuentasFacadeLocal cuentasfl;
    private LazyDataModel<Cuentas> cuentasldm;
    private Cuentas cuenta;
    private boolean crear = true;
    private boolean rmedit = false;
    private List<Cuentas> lista;
    private Messages msg = new Messages();

    public List<Cuentas> find() {
        lista = new ArrayList<>();
        try {
            lista = cuentasfl.findAll();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return lista;
    }

    @PostConstruct
    private void init() {
        cuenta = new Cuentas();
        try {
            this.cuentasldm = new LazyDataModel<Cuentas>() {
                @Override
                public Object getRowKey(Cuentas object) {
                    if (object != null) {
                        return object.getIdCuenta();
                    }
                    return null;
                }

                @Override
                public Cuentas getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Cuentas thi : (List<Cuentas>) getWrappedData()) {
                                if (thi.getIdCuenta().compareTo(buscado) == 0) {
                                    return thi;
                                }
                            }
                        } catch (Exception e) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                        }
                    }
                    return null;
                }

                @Override
                public List<Cuentas> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Cuentas> salida = new ArrayList();
                    try {
                        if (cuentasfl != null) {
                            this.setRowCount(cuentasfl.count());
                            salida = cuentasfl.findRange(first, pageSize);
                        }
                    } catch (Exception e) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    setLista(salida);
                    return salida;
                }
            };

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        
    }

    public void crear() {
        try {
            if (cuenta.getIdCuenta() != null && cuenta.getNombre().isEmpty() != true && cuenta.getTipoSaldo()!= null && this.cuentasfl != null) {
                this.cuentasfl.create(cuenta);
                clear();
               // msg.MsgCreado(); NO HAY NECESIDAD DE PONERLO AQUI, ES MEJOR EN LA REGLA DE NEGOCIO
            } else {
                msg.MsgIncompleto();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void eliminar() {
        try {
            if (cuenta != null && this.cuentasfl != null) {
                this.cuentasfl.remove(cuenta);
                clear();
                //msg.MsgBorrado(); NO HAY NECESIDAD DE PONERLO AQUI, ES MEJOR EN LA REGLA DE NEGOCIO
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void modificar() {
        try {
            if (cuenta != null && this.cuentasfl != null) {
                this.cuentasfl.edit(cuenta);
                clear();
               // msg.MsgModificado(); NO HAY NECESIDAD DE PONERLO AQUI, ES MEJOR EN LA REGLA DE NEGOCIO
            } else {
                msg.MsgIncompleto();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void changeSelected(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.cuenta = (Cuentas) se.getObject();
                this.crear = false;
                this.rmedit = true;
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void clear() {
        this.setCuenta(new Cuentas());
        this.crear = true;
        this.rmedit = false;
    }
    
    /*-------------- Getter and Setter ---------------------*/

    public CuentasFacadeLocal getCuentasfl() {
        return cuentasfl;
    }

    public void setCuentasfl(CuentasFacadeLocal cuentasfl) {
        this.cuentasfl = cuentasfl;
    }

    public LazyDataModel<Cuentas> getCuentasldm() {
        return cuentasldm;
    }

    public void setCuentasldm(LazyDataModel<Cuentas> cuentasldm) {
        this.cuentasldm = cuentasldm;
    }

    public Cuentas getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuentas cuenta) {
        this.cuenta = cuenta;
    }

    public boolean isCrear() {
        return crear;
    }

    public void setCrear(boolean crear) {
        this.crear = crear;
    }

    public boolean isRmedit() {
        return rmedit;
    }

    public void setRmedit(boolean rmedit) {
        this.rmedit = rmedit;
    }

    public List<Cuentas> getLista() {
        return lista;
    }

    public void setLista(List<Cuentas> lista) {
        this.lista = lista;
    }

    public Messages getMsg() {
        return msg;
    }

    public void setMsg(Messages msg) {
        this.msg = msg;
    }

}
