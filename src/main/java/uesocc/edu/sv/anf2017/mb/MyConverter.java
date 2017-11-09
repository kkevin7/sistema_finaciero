/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

/**
 *
 * @author yovany
 */
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import uesocc.edu.sv.anf2017.ejb.CuentasFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;

@FacesConverter("myconverter")
public class MyConverter implements Converter {

    CuentasFacadeLocal fl;
    Cuentas cuenta;
    @Override
    public Cuentas getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                int id = Integer.parseInt(value);
                Object algo;
                cuenta = fl.find(id);
//                System.out.println(algo);
                return cuenta;
//                CuentasFacadeLocal fl = (CuentasFacadeLocal) fc.getExternalContext().getApplicationMap().get("frmCont");
//                return fl.findAll().get(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Cuentas) object).getIdCuenta());
        } else {
            return null;
        }
    }

    public CuentasFacadeLocal getFl() {
        return fl;
    }

    public void setFl(CuentasFacadeLocal fl) {
        this.fl = fl;
    }
}
