/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uesocc.edu.sv.anf2017.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yovany
 */
@Entity
@Table(name = "movimientos", catalog = "analisisf2017", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimientos.findAll", query = "SELECT m FROM Movimientos m")
    , @NamedQuery(name = "Movimientos.inventarioB", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta = '1105000' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.findByIdMovimiento", query = "SELECT m FROM Movimientos m WHERE m.idMovimiento = :idMovimiento")
    , @NamedQuery(name = "Movimientos.cuentasCobrar", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta = '11020000' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.cuentasPagar", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta = '21010000' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.findByMonto", query = "SELECT m FROM Movimientos m WHERE m.monto = :monto")
    , @NamedQuery(name = "Movimientos.findByFecha", query = "SELECT m FROM Movimientos m WHERE m.fecha = :fecha")
    , @NamedQuery(name = "Movimientos.ventas", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '5101%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.rebVentas", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '5102%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.inventarioIni", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '5103%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.inventarioFin", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '5106%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.compras", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '4101%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.gastosCompras", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '4108%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.rebCompras", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '4102%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.otrosIngresos", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '5105%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.otrosGastos", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '4107%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.gastoOperativo", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '4103%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.gastoAdm", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '4104%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.gastoVentas", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '4105%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.gastoFinanciero", query = "select sum(t.monto) from Movimientos t where t.idCuenta.idCuenta like '4106%' and t.fecha like '2017%'")
    , @NamedQuery(name = "Movimientos.activosCorrientePOSI", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '11%' AND c.tipoSaldo='H' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.activosCorrienteNEG", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '11%' AND c.tipoSaldo='D' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.activosNOCorrientesPOSI", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '12%' AND c.tipoSaldo='H' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.activosNOCorrientesNEG", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '12%' AND c.tipoSaldo='D' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.pasivosCorrientesPOSI", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '21%' AND c.tipoSaldo='D' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.pasivosCorrientesNEG", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '21%' AND c.tipoSaldo='H' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.pasivosNOCorrientesPOSI", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '22%' AND c.tipoSaldo='D' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.pasivosNOCorrientesNEG", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '22%' AND c.tipoSaldo='H' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.capitalPOSI", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '3%' AND c.tipoSaldo='H' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.capitalNEG", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '3%' AND c.tipoSaldo='D' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.totalActivosPOSI", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '1%' AND c.tipoSaldo='H' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.totalActivosNEG", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '1%' AND c.tipoSaldo='D' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.activosOtrosPOSI", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '13%' AND c.tipoSaldo='H' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.activosOtrosNEG", query = "SELECT SUM(m.monto) FROM Movimientos m JOIN m.idCuenta c WHERE c.idCuenta LIKE '13%' AND c.tipoSaldo='D' AND m.fecha BETWEEN '2017-11-1' AND '2017-11-30' ORDER BY m.fecha ASC")
    , @NamedQuery(name = "Movimientos.findByDescripcion", query = "SELECT m FROM Movimientos m WHERE m.descripcion = :descripcion")})
public class Movimientos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_movimiento", nullable = false)
    private Integer idMovimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto", nullable = false)
    private double monto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 45)
    @Column(name = "descripcion", length = 45)
    private String descripcion;
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta", nullable = false)
    @ManyToOne(optional = false)
    private Cuentas idCuenta;
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa", nullable = false)
    @ManyToOne(optional = false)
    private Empresas idEmpresa;

    public Movimientos() {
    }

    public Movimientos(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Movimientos(Integer idMovimiento, double monto, Date fecha) {
        this.idMovimiento = idMovimiento;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cuentas getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Cuentas idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Empresas getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresas idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimiento != null ? idMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientos)) {
            return false;
        }
        Movimientos other = (Movimientos) object;
        if ((this.idMovimiento == null && other.idMovimiento != null) || (this.idMovimiento != null && !this.idMovimiento.equals(other.idMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uesocc.edu.sv.anf2017.entities.Movimientos[ idMovimiento=" + idMovimiento + " ]";
    }

}
