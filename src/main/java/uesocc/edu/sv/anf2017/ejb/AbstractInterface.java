/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.ejb;

import java.util.List;

/**
 *
 * @author yovany
 */

public interface AbstractInterface<T> {
    
    public void create(T t);

    public void edit(T t);

    public void remove(T t);

    public T find(Object id);

    public List<T> findAll();
    
    public List<T> findBy(String parameter, String value);
    
    public List<T> findByJoined(String parameter, Object value);

    public List<T> findRange(int first, int pageSize);

    public int count();
    
   
    
}
