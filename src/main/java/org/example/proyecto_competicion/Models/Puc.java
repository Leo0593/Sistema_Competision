package org.example.proyecto_competicion.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Puc {

    @Basic
    @Column(name = "usuario", insertable = false, updatable = false)  // Indicar que no es insertable ni actualizable
    private Integer usuario;

    @Basic
    @Column(name = "competencia", insertable = false, updatable = false)  // Indicar que no es insertable ni actualizable
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

    // Métodos getter y setter
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
