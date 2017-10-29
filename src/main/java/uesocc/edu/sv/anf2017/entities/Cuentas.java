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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cuentas", catalog = "analisisf2017", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuentas.findAll", query = "SELECT c FROM Cuentas c")
    , @NamedQuery(name = "Cuentas.findByIdCuenta", query = "SELECT c FROM Cuentas c WHERE c.idCuenta = :idCuenta")
    , @NamedQuery(name = "Cuentas.findByNombre", query = "SELECT c FROM Cuentas c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Cuentas.findByTipoSaldo", query = "SELECT c FROM Cuentas c WHERE c.tipoSaldo = :tipoSaldo")
    , @NamedQuery(name = "Cuentas.findByDescripcion", query = "SELECT c FROM Cuentas c WHERE c.descripcion = :descripcion")})
public class Cuentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cuenta", nullable = false)
    private Integer idCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_saldo", nullable = false)
    private Character tipoSaldo;
    @Size(max = 45)
    @Column(name = "descripcion", length = 45)
    private String descripcion;
    @JoinColumn(name = "id_tipo_cuenta", referencedColumnName = "id_tipo_cuenta", nullable = false)
    @ManyToOne(optional = false)
    private TipoCuenta idTipoCuenta;
    @JoinColumn(name = "id_tipo_estado", referencedColumnName = "id_tipo_estado", nullable = false)
    @ManyToOne(optional = false)
    private TipoEstado idTipoEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuenta")
    private List<Movimientos> movimientosList;

    public Cuentas() {
    }

    public Cuentas(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Cuentas(Integer idCuenta, String nombre, Character tipoSaldo) {
        this.idCuenta = idCuenta;
        this.nombre = nombre;
        this.tipoSaldo = tipoSaldo;
    }
    
    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getTipoSaldo() {
        return tipoSaldo;
    }

    public void setTipoSaldo(Character tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoCuenta getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(TipoCuenta idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public TipoEstado getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(TipoEstado idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    @XmlTransient
    public List<Movimientos> getMovimientosList() {
        return movimientosList;
    }

    public void setMovimientosList(List<Movimientos> movimientosList) {
        this.movimientosList = movimientosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuentas)) {
            return false;
        }
        Cuentas other = (Cuentas) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uesocc.edu.sv.anf2017.entities.Cuentas[ idCuenta=" + idCuenta + " ]";
    }
    
}
