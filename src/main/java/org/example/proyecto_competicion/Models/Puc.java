package org.example.proyecto_competicion.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Puc {
    @Basic
    @Column(name = "usuario")
    private Integer usuario;
    @Basic
    @Column(name = "competencia")
    private Integer competencia;
    @Basic
    @Column(name = "puntaje")
    private Integer puntaje;
    @Basic
    @Column(name = "fecha_inscripcion")
    private Timestamp fechaInscripcion;
    @Basic
    @Column(name = "posicion_final")
    private Integer posicionFinal;
    @Basic
    @Column(name = "estado_participacion")
    private Object estadoParticipacion;
    @Basic
    @Column(name = "en_equipo")
    private String enEquipo;
    @Basic
    @Column(name = "fecha_actualizacion")
    private Timestamp fechaActualizacion;
    @Basic
    @Column(name = "pago_realizado")
    private Object pagoRealizado;
    @Basic
    @Column(name = "fecha_pago")
    private Timestamp fechaPago;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codi")
    private int codi;
    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "codi")
    private Usuarios usuariosByUsuario;
    @ManyToOne
    @JoinColumn(name = "competencia", referencedColumnName = "codi")
    private Competicion competicionByCompetencia;

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Integer competencia) {
        this.competencia = competencia;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Timestamp getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Timestamp fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Integer getPosicionFinal() {
        return posicionFinal;
    }

    public void setPosicionFinal(Integer posicionFinal) {
        this.posicionFinal = posicionFinal;
    }

    public Object getEstadoParticipacion() {
        return estadoParticipacion;
    }

    public void setEstadoParticipacion(Object estadoParticipacion) {
        this.estadoParticipacion = estadoParticipacion;
    }

    public String getEnEquipo() {
        return enEquipo;
    }

    public void setEnEquipo(String enEquipo) {
        this.enEquipo = enEquipo;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Object getPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(Object pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }

    public Timestamp getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Timestamp fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Puc puc = (Puc) o;

        if (codi != puc.codi) return false;
        if (usuario != null ? !usuario.equals(puc.usuario) : puc.usuario != null) return false;
        if (competencia != null ? !competencia.equals(puc.competencia) : puc.competencia != null) return false;
        if (puntaje != null ? !puntaje.equals(puc.puntaje) : puc.puntaje != null) return false;
        if (fechaInscripcion != null ? !fechaInscripcion.equals(puc.fechaInscripcion) : puc.fechaInscripcion != null)
            return false;
        if (posicionFinal != null ? !posicionFinal.equals(puc.posicionFinal) : puc.posicionFinal != null) return false;
        if (estadoParticipacion != null ? !estadoParticipacion.equals(puc.estadoParticipacion) : puc.estadoParticipacion != null)
            return false;
        if (enEquipo != null ? !enEquipo.equals(puc.enEquipo) : puc.enEquipo != null) return false;
        if (fechaActualizacion != null ? !fechaActualizacion.equals(puc.fechaActualizacion) : puc.fechaActualizacion != null)
            return false;
        if (pagoRealizado != null ? !pagoRealizado.equals(puc.pagoRealizado) : puc.pagoRealizado != null) return false;
        if (fechaPago != null ? !fechaPago.equals(puc.fechaPago) : puc.fechaPago != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usuario != null ? usuario.hashCode() : 0;
        result = 31 * result + (competencia != null ? competencia.hashCode() : 0);
        result = 31 * result + (puntaje != null ? puntaje.hashCode() : 0);
        result = 31 * result + (fechaInscripcion != null ? fechaInscripcion.hashCode() : 0);
        result = 31 * result + (posicionFinal != null ? posicionFinal.hashCode() : 0);
        result = 31 * result + (estadoParticipacion != null ? estadoParticipacion.hashCode() : 0);
        result = 31 * result + (enEquipo != null ? enEquipo.hashCode() : 0);
        result = 31 * result + (fechaActualizacion != null ? fechaActualizacion.hashCode() : 0);
        result = 31 * result + (pagoRealizado != null ? pagoRealizado.hashCode() : 0);
        result = 31 * result + (fechaPago != null ? fechaPago.hashCode() : 0);
        result = 31 * result + codi;
        return result;
    }

    public Usuarios getUsuariosByUsuario() {
        return usuariosByUsuario;
    }

    public void setUsuariosByUsuario(Usuarios usuariosByUsuario) {
        this.usuariosByUsuario = usuariosByUsuario;
    }

    public Competicion getCompeticionByCompetencia() {
        return competicionByCompetencia;
    }

    public void setCompeticionByCompetencia(Competicion competicionByCompetencia) {
        this.competicionByCompetencia = competicionByCompetencia;
    }
}
