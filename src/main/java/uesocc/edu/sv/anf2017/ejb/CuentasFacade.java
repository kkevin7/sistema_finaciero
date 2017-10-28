/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uesocc.edu.sv.anf2017.entities.Cuentas;

/**
 *
 * @author yovany
 */
@Stateless
public class CuentasFacade extends AbstractFacade<Cuentas> implements CuentasFacadeLocal {

    @PersistenceContext(unitName = "uesocc.edu.sv_anf2017_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentasFacade() {
        super(Cuentas.class);
    }
    
}
