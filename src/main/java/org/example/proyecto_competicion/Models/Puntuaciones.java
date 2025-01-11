package org.example.proyecto_competicion.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Puntuaciones {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "inscripcion_id")
    private int inscripcionId;
    @Basic
    @Column(name = "puntaje")
    private int puntaje;
    @Basic
    @Column(name = "fecha")
    private Timestamp fecha;
    @ManyToOne
    @JoinColumn(name = "inscripcion_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Inscripcion inscripcionByInscripcionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(int inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Puntuaciones that = (Puntuaciones) o;

        if (id != that.id) return false;
        if (inscripcionId != that.inscripcionId) return false;
        if (puntaje != that.puntaje) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + inscripcionId;
        result = 31 * result + puntaje;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        return result;
    }

    public Inscripcion getInscripcionByInscripcionId() {
        return inscripcionByInscripcionId;
    }

    public void setInscripcionByInscripcionId(Inscripcion inscripcionByInscripcionId) {
        this.inscripcionByInscripcionId = inscripcionByInscripcionId;
    }
}
