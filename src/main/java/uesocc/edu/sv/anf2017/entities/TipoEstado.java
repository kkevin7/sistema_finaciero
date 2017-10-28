/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "tipo_estado", catalog = "analisisf2017", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEstado.findAll", query = "SELECT t FROM TipoEstado t")
    , @NamedQuery(name = "TipoEstado.findByIdTipoEstado", query = "SELECT t FROM TipoEstado t WHERE t.idTipoEstado = :idTipoEstado")
    , @NamedQuery(name = "TipoEstado.findByNombre", query = "SELECT t FROM TipoEstado t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoEstado.findByDescripcion", query = "SELECT t FROM TipoEstado t WHERE t.descripcion = :descripcion")})
public class TipoEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_estado", nullable = false)
    private Integer idTipoEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @Size(max = 45)
    @Column(name = "descripcion", length = 45)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoEstado")
    private List<Cuentas> cuentasList;

    public TipoEstado() {
    }

    public TipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public TipoEstado(Integer idTipoEstado, String nombre) {
        this.idTipoEstado = idTipoEstado;
        this.nombre = nombre;
    }

    public Integer getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Cuentas> getCuentasList() {
        return cuentasList;
    }

    public void setCuentasList(List<Cuentas> cuentasList) {
        this.cuentasList = cuentasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEstado != null ? idTipoEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEstado)) {
            return false;
        }
        TipoEstado other = (TipoEstado) object;
        if ((this.idTipoEstado == null && other.idTipoEstado != null) || (this.idTipoEstado != null && !this.idTipoEstado.equals(other.idTipoEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uesocc.edu.sv.anf2017.entities.TipoEstado[ idTipoEstado=" + idTipoEstado + " ]";
    }
    
}
