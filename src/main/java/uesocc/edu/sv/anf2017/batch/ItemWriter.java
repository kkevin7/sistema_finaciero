/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.batch;


import java.util.List;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import uesocc.edu.sv.anf2017.ejb.CuentasFacadeLocal;
import uesocc.edu.sv.anf2017.entities.Cuentas;


@Dependent
@Named(value="itemWriter")
public class ItemWriter extends AbstractItemWriter {

@EJB
private CuentasFacadeLocal ejbCuentas;

    @Override
    public void writeItems(List list) {
        System.out.println("writeItems: " + list);
        for (Object cuentas : list) {
            ejbCuentas.create((Cuentas)cuentas);
        }
    }
}
