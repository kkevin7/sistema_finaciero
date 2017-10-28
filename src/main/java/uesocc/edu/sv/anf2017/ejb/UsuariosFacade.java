/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uesocc.edu.sv.anf2017.entities.Usuarios;

/**
 *
 * @author yovany
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "uesocc.edu.sv_anf2017_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
}
