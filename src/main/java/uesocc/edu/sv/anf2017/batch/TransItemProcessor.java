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
import uesocc.edu.sv.anf2017.ejb.EmpresasFacadeLocal;
import uesocc.edu.sv.anf2017.ejb.TipoCuentaFacadeLocal;
import uesocc.edu.sv.anf2017.ejb.TipoEstadoFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;
import uesocc.edu.sv.anf2017.entities.Empresas;
import uesocc.edu.sv.anf2017.entities.Movimientos;

@Dependent
@Named(value = "transItemProcessor")
public class TransItemProcessor implements ItemProcessor {

    private Movimientos nmovimiento;
    private SimpleDateFormat datehelper = new SimpleDateFormat("yyyy-MM-dd");
    @Inject
    protected JobContext jobContext;
    @EJB
    private EmpresasFacadeLocal ejbEmpresa;
    @EJB
    private CuentasFacadeLocal ejbCuenta;
    private String rs;
    private char cr;

    @Override

    public Object processItem(Object item) {
        nmovimiento = new Movimientos();
        System.out.println("processItem: " + item.toString());
       
     

        try {
            rs = item.toString();
            
            StringTokenizer tokens = new StringTokenizer(rs, ",");
            
            nmovimiento.setIdEmpresa(ejbEmpresa.find(Integer.valueOf(tokens.nextToken())));
            nmovimiento.setIdCuenta(ejbCuenta.find(Integer.valueOf(tokens.nextToken())));
            nmovimiento.setMonto(Double.valueOf(tokens.nextToken()));
            nmovimiento.setFecha(datehelper.parse(tokens.nextToken())); 
            if (!tokens.hasMoreTokens() ) {
                nmovimiento.setDescripcion("Sin detalles");
            } else {
                nmovimiento.setDescripcion(tokens.toString());
            }

        } catch (Exception ex) {
            Logger.getLogger(ItemProcessor.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(item.toString());
            return null;

        } 

              return nmovimiento;
       
    }
}
