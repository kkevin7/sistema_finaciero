
package uesocc.edu.sv.anf2017.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import uesocc.edu.sv.anf2017.mb.Messages;

public abstract class AbstractFacade<T> {

    private Messages msg = new Messages();
    private EntityManager em;

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        
        em= getEntityManager();
        try {
            if(em != null && entity != null){
            getEntityManager().persist(entity);

             msg.MsgCreado();
            }
            else{
                msg.msgEntidadVacia();
                System.err.println("La entidad que llego a la regla de negocio se encuentra vacia");
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void edit(T entity) {
        em= getEntityManager();
        try {
            if(em != null && entity != null){
            getEntityManager().merge(entity);
            msg.MsgModificado();
            }
            else{
                msg.msgEntidadVacia();
                System.err.println("La entidad que llego a la regla de negocio se encuentra vacia");
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void remove(T entity) {
        em= getEntityManager();
        try {
            if(em != null && entity != null){
            getEntityManager().remove(getEntityManager().merge(entity));
            msg.MsgBorrado();
            }
            else{
                msg.msgEntidadVacia();
                System.err.println("La entidad que llego a la regla de negocio se encuentra vacia");
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int first, int pageSize) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findBy(String parameter, String value){
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        c.select(t).where(cb.like(t.<String>get(parameter), value+"%"));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        return q.getResultList();
    }
    
    public List<T> findByJoined(String parameter, Object value){
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> c = cb.createQuery(entityClass);
        javax.persistence.criteria.Root<T> t = c.from(entityClass);
        t.join(parameter);
        c.select(t).where(cb.equal(t.get(parameter), value));
        javax.persistence.Query q = getEntityManager().createQuery(c);
        return q.getResultList();
    }
    
    
}
