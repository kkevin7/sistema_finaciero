package uesocc.edu.sv.anf2017.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import uesocc.edu.sv.anf2017.ejb.MovimientosFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Movimientos;

@Named(value = "frmMovimientos")
@ViewScoped
public class FrmMovimientos implements Serializable {

    @EJB
    private MovimientosFacadeLocal ejbMovimientos;
    private Movimientos movimientos;
    private List<Movimientos> lista;
    private LazyDataModel<Movimientos> ldmMovimientos;
    private Messages msg = new Messages();
    /*-- botones --*/
    private boolean crear = true;
    private boolean rmedit = false;

    public FrmMovimientos() {
    }

    @Deprecated
    public List<Movimientos> obtenerTodos() {
        List<Movimientos> salida = new ArrayList();
        try {
            if (ejbMovimientos != null) {
                salida = ejbMovimientos.findAll();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return salida;
    }

    @PostConstruct
    private void init() {
        movimientos = new Movimientos();
        try {
            this.ldmMovimientos = new LazyDataModel<Movimientos>() {
                @Override
                public Object getRowKey(Movimientos object) {
                    if (object != null) {
                        return object.getIdMovimiento();
                    }
                    return null;
                }

                @Override
                public Movimientos getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Movimientos thi : (List<Movimientos>) getWrappedData()) {
                                if (thi.getIdMovimiento().compareTo(buscado) == 0) {
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
                public List<Movimientos> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Movimientos> salida = new ArrayList();
                    try {
                        if (ejbMovimientos != null) {
                            this.setRowCount(ejbMovimientos.count());
                            salida = ejbMovimientos.findRange(first, pageSize);
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

        if (this.movimientos.getFecha() != null) {

            try {
                if (this.movimientos != null && this.ejbMovimientos != null) {
                    ejbMovimientos.create(this.movimientos);
                    clear();
                } else {
                    msg.MsgIncompleto();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void eliminar() {
        try {
            if (this.movimientos != null && this.ejbMovimientos != null) {
                ejbMovimientos.remove(this.movimientos);
            } else {
                msg.MsgIncompleto();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void modificar() {

        if (this.movimientos.getFecha() != null) {

            try {
                if (this.movimientos != null && this.ejbMovimientos != null) {
                    ejbMovimientos.edit(this.movimientos);
                } else {
                    msg.MsgIncompleto();
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void changeSelected(SelectEvent se) {
        if (se.getObject() != null) {
            try {
                this.movimientos = (Movimientos) se.getObject();
                this.crear = false;
                this.rmedit = true;
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void clear() {
        this.setMovimientos(new Movimientos());
        this.crear = true;
        this.rmedit = false;
    }
    
    /**
     * ------------- Setter and Getter -------------
     *
     * @return -
     */


    public MovimientosFacadeLocal getEjbMovimientos() {
        return ejbMovimientos;
    }

    public void setEjbMovimientos(MovimientosFacadeLocal ejbMovimientos) {
        this.ejbMovimientos = ejbMovimientos;
    }

    public Movimientos getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimientos movimientos) {
        this.movimientos = movimientos;
    }

    public List<Movimientos> getLista() {
        return lista;
    }

    public void setLista(List<Movimientos> lista) {
        this.lista = lista;
    }

    public LazyDataModel<Movimientos> getLdmMovimientos() {
        return ldmMovimientos;
    }

    public void setLdmMovimientos(LazyDataModel<Movimientos> ldmMovimientos) {
        this.ldmMovimientos = ldmMovimientos;
    }

    public Messages getMsg() {
        return msg;
    }

    public void setMsg(Messages msg) {
        this.msg = msg;
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

}
