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
@Table(name = "tipo_cuenta", catalog = "analisisf2017", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCuenta.findAll", query = "SELECT t FROM TipoCuenta t")
    , @NamedQuery(name = "TipoCuenta.findByIdTipoCuenta", query = "SELECT t FROM TipoCuenta t WHERE t.idTipoCuenta = :idTipoCuenta")
    , @NamedQuery(name = "TipoCuenta.findByNombre", query = "SELECT t FROM TipoCuenta t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoCuenta.findByDescripcion", query = "SELECT t FROM TipoCuenta t WHERE t.descripcion = :descripcion")})
public class TipoCuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_cuenta", nullable = false)
    private Integer idTipoCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @Size(max = 45)
    @Column(name = "descripcion", length = 45)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoCuenta")
    private List<Cuentas> cuentasList;

    public TipoCuenta() {
    }

    public TipoCuenta(Integer idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public TipoCuenta(Integer idTipoCuenta, String nombre) {
        this.idTipoCuenta = idTipoCuenta;
        this.nombre = nombre;
    }

    public Integer getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(Integer idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
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
        hash += (idTipoCuenta != null ? idTipoCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCuenta)) {
            return false;
        }
        TipoCuenta other = (TipoCuenta) object;
        if ((this.idTipoCuenta == null && other.idTipoCuenta != null) || (this.idTipoCuenta != null && !this.idTipoCuenta.equals(other.idTipoCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uesocc.edu.sv.anf2017.entities.TipoCuenta[ idTipoCuenta=" + idTipoCuenta + " ]";
    }
    
}
