package org.example.proyecto_competicion.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Usuario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "apellido")
    private String apellido;
    @Basic
    @Column(name = "rol")
    private String rol;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @Basic
    @Column(name = "reset_token")
    private String resetToken;
    @Basic
    @Column(name = "avatar")
    private String avatar;
    @Basic
    @Column(name = "estado")
    private Byte estado;
    @Basic
    @Column(name = "historial")
    private String historial;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "usuarioByIdCreador")
    private Collection<Competicion> competicionsById;
    @OneToMany(mappedBy = "usuarioByUsuario")
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Byte getEstado() {
        return estado;
    }

    public void setEstado(Byte estado) {
        this.estado = estado;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
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

        Usuario usuario = (Usuario) o;

        if (id != usuario.id) return false;
        if (nombre != null ? !nombre.equals(usuario.nombre) : usuario.nombre != null) return false;
        if (apellido != null ? !apellido.equals(usuario.apellido) : usuario.apellido != null) return false;
        if (rol != null ? !rol.equals(usuario.rol) : usuario.rol != null) return false;
        if (correo != null ? !correo.equals(usuario.correo) : usuario.correo != null) return false;
        if (contrasena != null ? !contrasena.equals(usuario.contrasena) : usuario.contrasena != null) return false;
        if (avatar != null ? !avatar.equals(usuario.avatar) : usuario.avatar != null) return false;
        if (estado != null ? !estado.equals(usuario.estado) : usuario.estado != null) return false;
        if (historial != null ? !historial.equals(usuario.historial) : usuario.historial != null) return false;
        if (createdAt != null ? !createdAt.equals(usuario.createdAt) : usuario.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(usuario.updatedAt) : usuario.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
        result = 31 * result + (rol != null ? rol.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (contrasena != null ? contrasena.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (historial != null ? historial.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    public Collection<Competicion> getCompeticionsById() {
        return competicionsById;
    }

    public void setCompeticionsById(Collection<Competicion> competicionsById) {
        this.competicionsById = competicionsById;
    }

    public Collection<Inscripcion> getInscripcionsById() {
        return inscripcionsById;
    }

    public void setInscripcionsById(Collection<Inscripcion> inscripcionsById) {
        this.inscripcionsById = inscripcionsById;
    }
}
