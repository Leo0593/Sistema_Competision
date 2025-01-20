package org.example.proyecto_competicion.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Inscripcion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "usuario")
    private int usuario;
    @Basic
    @Column(name = "competencia")
    private int competencia;
    @Basic
    @Column(name = "en_equipo")
    private Byte enEquipo;
    @Basic
    @Column(name = "nombre_equipo")
    private String nombreEquipo;
    @Basic
    @Column(name = "pago_realizado")
    private Byte pagoRealizado;
    @Basic
    @Column(name = "fecha_pago")
    private Timestamp fechaPago;
    @Basic
    @Column(name = "correo_participantes")
    private String correoParticipantes;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "id", nullable = false,  insertable = false, updatable = false)
    private Usuario usuarioByUsuario;
    @ManyToOne
    @JoinColumn(name = "competencia", referencedColumnName = "id", nullable = false,  insertable = false, updatable = false)
    private Competicion competicionByCompetencia;
    @OneToMany(mappedBy = "inscripcionByInscripcionId")
    private Collection<Puntuaciones> puntuacionesById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getCompetencia() {
        return competencia;
    }

    public void setCompetencia(int competencia) {
        this.competencia = competencia;
    }

    public Byte getEnEquipo() {
        return enEquipo;
    }

    public void setEnEquipo(Byte enEquipo) {
        this.enEquipo = enEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public Byte getPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(Byte pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }

    public Timestamp getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Timestamp fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCorreoParticipantes() {
        return correoParticipantes;
    }

    public void setCorreoParticipantes(String correoParticipantes) {
        this.correoParticipantes = correoParticipantes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inscripcion that = (Inscripcion) o;

        if (id != that.id) return false;
        if (usuario != that.usuario) return false;
        if (competencia != that.competencia) return false;
        if (enEquipo != null ? !enEquipo.equals(that.enEquipo) : that.enEquipo != null) return false;
        if (nombreEquipo != null ? !nombreEquipo.equals(that.nombreEquipo) : that.nombreEquipo != null) return false;
        if (pagoRealizado != null ? !pagoRealizado.equals(that.pagoRealizado) : that.pagoRealizado != null)
            return false;
        if (fechaPago != null ? !fechaPago.equals(that.fechaPago) : that.fechaPago != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + usuario;
        result = 31 * result + competencia;
        result = 31 * result + (enEquipo != null ? enEquipo.hashCode() : 0);
        result = 31 * result + (nombreEquipo != null ? nombreEquipo.hashCode() : 0);
        result = 31 * result + (pagoRealizado != null ? pagoRealizado.hashCode() : 0);
        result = 31 * result + (fechaPago != null ? fechaPago.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    public Usuario getUsuarioByUsuario() {
        return usuarioByUsuario;
    }

    public void setUsuarioByUsuario(Usuario usuarioByUsuario) {
        this.usuarioByUsuario = usuarioByUsuario;
    }

    public Competicion getCompeticionByCompetencia() {
        return competicionByCompetencia;
    }

    public void setCompeticionByCompetencia(Competicion competicionByCompetencia) {
        this.competicionByCompetencia = competicionByCompetencia;
    }

    public Collection<Puntuaciones> getPuntuacionesById() {
        return puntuacionesById;
    }

    public void setPuntuacionesById(Collection<Puntuaciones> puntuacionesById) {
        this.puntuacionesById = puntuacionesById;
    }
}
