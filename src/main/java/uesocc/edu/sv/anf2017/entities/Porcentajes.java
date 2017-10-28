/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "porcentajes", catalog = "analisisf2017", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Porcentajes.findAll", query = "SELECT p FROM Porcentajes p")
    , @NamedQuery(name = "Porcentajes.findByIdPorcentaje", query = "SELECT p FROM Porcentajes p WHERE p.idPorcentaje = :idPorcentaje")
    , @NamedQuery(name = "Porcentajes.findByNombre", query = "SELECT p FROM Porcentajes p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Porcentajes.findByPorcentaje", query = "SELECT p FROM Porcentajes p WHERE p.porcentaje = :porcentaje")
    , @NamedQuery(name = "Porcentajes.findByDescripcion", query = "SELECT p FROM Porcentajes p WHERE p.descripcion = :descripcion")})
public class Porcentajes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_porcentaje", nullable = false)
    private Integer idPorcentaje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje", nullable = false, precision = 10, scale = 4)
    private BigDecimal porcentaje;
    @Size(max = 45)
    @Column(name = "descripcion", length = 45)
    private String descripcion;

    public Porcentajes() {
    }

    public Porcentajes(Integer idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public Porcentajes(Integer idPorcentaje, String nombre, BigDecimal porcentaje) {
        this.idPorcentaje = idPorcentaje;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public Integer getIdPorcentaje() {
        return idPorcentaje;
    }

    public void setIdPorcentaje(Integer idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPorcentaje != null ? idPorcentaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Porcentajes)) {
            return false;
        }
        Porcentajes other = (Porcentajes) object;
        if ((this.idPorcentaje == null && other.idPorcentaje != null) || (this.idPorcentaje != null && !this.idPorcentaje.equals(other.idPorcentaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uesocc.edu.sv.anf2017.entities.Porcentajes[ idPorcentaje=" + idPorcentaje + " ]";
    }
    
}
