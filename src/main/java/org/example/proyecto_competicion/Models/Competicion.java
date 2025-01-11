package org.example.proyecto_competicion.Models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class Competicion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "fecha_inicio")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")  // Usamos LocalDateTime
    private LocalDateTime fechaInicio;
    @Basic
    @Column(name = "fecha_fin")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")  // Usamos LocalDateTime
    private LocalDateTime fechaFin;
    @Basic
    @Column(name = "estado")
    private Byte estado;
    @Basic
    @Column(name = "id_creador")
    private int idCreador;
    @Basic
    @Column(name = "tipo")
    private Object tipo;
    @Basic
    @Column(name = "id_categoria")
    private int idCategoria;
    @Basic
    @Column(name = "precio_inscripcion")
    private int precioInscripcion;
    @Basic
    @Column(name = "personas_por_grupo")
    private Integer personasPorGrupo;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @ManyToOne
    @JoinColumn(name = "id_creador", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Usuario usuarioByIdCreador;
    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Categoria categoriaByIdCategoria;
    @OneToMany(mappedBy = "competicionByCompetencia")
    private Collection<Inscripcion> inscripcionsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Byte getEstado() {
        return estado;
    }

    public void setEstado(Byte estado) {
        this.estado = estado;
    }

    public int getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(int idCreador) {
        this.idCreador = idCreador;
    }

    public Object getTipo() {
        return tipo;
    }

    public void setTipo(Object tipo) {
        this.tipo = tipo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getPrecioInscripcion() {
        return precioInscripcion;
    }

    public void setPrecioInscripcion(int precioInscripcion) {
        this.precioInscripcion = precioInscripcion;
    }

    public Integer getPersonasPorGrupo() {
        return personasPorGrupo;
    }

    public void setPersonasPorGrupo(Integer personasPorGrupo) {
        this.personasPorGrupo = personasPorGrupo;
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

        Competicion that = (Competicion) o;

        if (id != that.id) return false;
        if (idCreador != that.idCreador) return false;
        if (idCategoria != that.idCategoria) return false;
        if (precioInscripcion != that.precioInscripcion) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (fechaInicio != null ? !fechaInicio.equals(that.fechaInicio) : that.fechaInicio != null) return false;
        if (fechaFin != null ? !fechaFin.equals(that.fechaFin) : that.fechaFin != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;
        if (personasPorGrupo != null ? !personasPorGrupo.equals(that.personasPorGrupo) : that.personasPorGrupo != null)
            return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (fechaInicio != null ? fechaInicio.hashCode() : 0);
        result = 31 * result + (fechaFin != null ? fechaFin.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + idCreador;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        result = 31 * result + idCategoria;
        result = 31 * result + precioInscripcion;
        result = 31 * result + (personasPorGrupo != null ? personasPorGrupo.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    public Usuario getUsuarioByIdCreador() {
        return usuarioByIdCreador;
    }

    public void setUsuarioByIdCreador(Usuario usuarioByIdCreador) {
        this.usuarioByIdCreador = usuarioByIdCreador;
    }

    public Categoria getCategoriaByIdCategoria() {
        return categoriaByIdCategoria;
    }

    public void setCategoriaByIdCategoria(Categoria categoriaByIdCategoria) {
        this.categoriaByIdCategoria = categoriaByIdCategoria;
    }

    public Collection<Inscripcion> getInscripcionsById() {
        return inscripcionsById;
    }

    public void setInscripcionsById(Collection<Inscripcion> inscripcionsById) {
        this.inscripcionsById = inscripcionsById;
    }
}
