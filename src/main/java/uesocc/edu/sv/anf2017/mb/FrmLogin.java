/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import uesocc.edu.sv.anf2017.entities.Usuarios;

/**
 *
 * @author phyroot
 */
@Named(value = "frmLogin")
@RequestScoped
public class FrmLogin implements Serializable{

    private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    private Map<String, Object> sessionMap = externalContext.getSessionMap();
    private EntityManager em;
    private Usuarios usuario = new Usuarios();
    private Usuarios user = (Usuarios) sessionMap.get("user");
    
    public void initialize(ComponentSystemEvent cse) throws IOException {

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {

        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");

        }
    }

    public void login() throws IOException {
       
        Usuarios nuevo = autenticar(usuario);
        if (nuevo!=null) {
            sessionMap.put("user", nuevo);
             
            externalContext.redirect("../index.xhtml");

        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acceso denegado", "Usuario o contraseña incorrecta");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void logout() throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");

    }

    public Usuarios autenticar(Usuarios user) {

        
             EntityManagerFactory emf = Persistence.createEntityManagerFactory("uesocc.edu.sv_anf2017_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

            Query query = em.createNamedQuery("Usuarios.auth");
            query.setParameter(1, user.getLogin()).setParameter(2, user.getClave());
            try {
                Usuarios ul = (Usuarios) query.getSingleResult();
                
                return ul;
            } catch (javax.persistence.NoResultException e) {
                return null;
            }
        
    }

    /**
     * @return the usuario
     */
    public Usuarios getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }
    
    

}
