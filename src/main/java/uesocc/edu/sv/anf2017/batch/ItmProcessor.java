/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.batch;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import uesocc.edu.sv.anf2017.ejb.CuentasFacadeLocal;
import uesocc.edu.sv.anf2017.ejb.TipoCuentaFacadeLocal;
import uesocc.edu.sv.anf2017.ejb.TipoEstadoFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;

@Dependent
@Named(value = "itemProcessor")
public class ItmProcessor implements ItemProcessor {

    private Cuentas ncuenta;
    @Inject
    protected JobContext jobContext;
    @EJB
    private TipoCuentaFacadeLocal ejbTipoCuenta;
    @EJB
    private TipoEstadoFacadeLocal ejbTipoEstado;
    private String rs;
    private char cr;

    @Override

    public Object processItem(Object item) {

        System.out.println("processItem: " + item.toString());
        try {
            rs = item.toString();
            StringTokenizer tokens = new StringTokenizer(rs, ",");
            ncuenta.setIdCuenta(Integer.valueOf(tokens.nextToken()));            
            ncuenta.setNombre(tokens.nextToken());
            ncuenta.setIdTipoCuenta(ejbTipoCuenta.find(Integer.valueOf(tokens.nextToken())));
            ncuenta.setIdTipoEstado(ejbTipoEstado.find(Integer.valueOf(tokens.nextToken())));
            ncuenta.setTipoSaldo(tokens.nextToken().charAt(0));
            if (tokens.nextToken().isEmpty()) {
                ncuenta.setDescripcion("Sin detalles");
            } else {
                ncuenta.setDescripcion(tokens.toString());
            }

        } catch (Exception ex) {
            Logger.getLogger(ItemProcessor.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(item.toString());
            return null;

        } 

              return ncuenta;
       
    }
}
