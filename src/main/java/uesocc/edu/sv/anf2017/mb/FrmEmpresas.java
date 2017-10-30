
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
import uesocc.edu.sv.anf2017.ejb.EmpresasFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Empresas;

@Named(value = "frmEmpresas")
@ViewScoped
public class FrmEmpresas implements Serializable{

    @EJB
    private EmpresasFacadeLocal ejbEmpresas;
    private Empresas empresas = new Empresas();
    private List<Empresas> lista;
    private LazyDataModel<Empresas> ldmEmpresas;
    private Messages msg = new Messages();
    /*-- botones --*/
    private boolean rcrear = true;
    private boolean rmedit = false;
    
    public FrmEmpresas() {
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
        
       
    @PostConstruct
    private void init() {
        empresas = new Empresas();
        try {
            this.ldmEmpresas = new LazyDataModel<Empresas>() {
                @Override
                public Object getRowKey(Empresas object) {
                    if (object != null) {
                        return object.getIdEmpresa();
                    }
                    return null;
                }

                @Override
                public Empresas getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Empresas thi : (List<Empresas>) getWrappedData()) {
                                if (thi.getIdEmpresa().compareTo(buscado) == 0) {
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
                public List<Empresas> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Empresas> salida = new ArrayList();
                    try {
                        if (ejbEmpresas != null) {
                            this.setRowCount(ejbEmpresas.count());
                            salida = ejbEmpresas.findRange(first, pageSize);
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
        if (this.empresas != null && this.ejbEmpresas != null) {
            try {
                if ( this.empresas.getNombre() != null && this.empresas.getGiro() != null) {
                    ejbEmpresas.create(this.empresas);
                    clear();
                } else {
                    msg.MsgIncompleto();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        else{
            msg.msgEntidadVacia();
        }
    }

    public void eliminar() {
        if (this.empresas != null && this.ejbEmpresas != null) {
        try {
            if (this.empresas.getNombre() != null  && this.empresas.getGiro() != null) {
                ejbEmpresas.remove(this.empresas);
            } else {
                msg.MsgIncompleto();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        } 
        else{
            msg.msgEntidadVacia();
        }
    }

    public void modificar() {

        if (this.empresas != null && this.ejbEmpresas != null) {

            try {
                if ( this.empresas.getNombre() != null && this.empresas.getGiro() != null) {
                    ejbEmpresas.edit(this.empresas);
                } else {
                    msg.MsgIncompleto();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        else{
            msg.msgEntidadVacia();
        }
    }

    public void changeSelected(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.empresas = (Empresas) se.getObject();
                this.rcrear = false;
                this.rmedit = true;
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void clear() {
        this.setEmpresas(new Empresas());
        this.rcrear = true;
        this.rmedit = false;
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

    public List<Empresas> getLista() {
        return lista;
    }

    public void setLista(List<Empresas> lista) {
        this.lista = lista;
    }

    public LazyDataModel<Empresas> getLdmEmpresas() {
        return ldmEmpresas;
    }

    public void setLdmEmpresas(LazyDataModel<Empresas> ldmEmpresas) {
        this.ldmEmpresas = ldmEmpresas;
    }

    public boolean isRcrear() {
        return rcrear;
    }

    public void setRcrear(boolean rcrear) {
        this.rcrear = rcrear;
    }

    public boolean isRmedit() {
        return rmedit;
    }

    public void setRmedit(boolean rmedit) {
        this.rmedit = rmedit;
    }


    
}