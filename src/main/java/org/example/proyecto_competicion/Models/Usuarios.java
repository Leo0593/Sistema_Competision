package org.example.proyecto_competicion.Models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
public class Usuarios {
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "apellido")
    private String apellido;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "codi")
    private int codi;
    @Basic
    @Column(name = "rol")
    private Object rol;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @Basic
    @Column(name = "avatar")
    private String avatar;
    @Basic
    @Column(name = "estado")
    private byte estado;
    @Basic
    @Column(name = "fecha_inscripcion")
    private Date fechaInscripcion;
    @Basic
    @Column(name = "historial")
    private String historial;
    @OneToMany(mappedBy = "usuariosByUsuario")
    private Collection<Puc> pucsByCodi;

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

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public Object getRol() {
        return rol;
    }

    public void setRol(Object rol) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuarios usuarios = (Usuarios) o;

        if (codi != usuarios.codi) return false;
        if (estado != usuarios.estado) return false;
        if (nombre != null ? !nombre.equals(usuarios.nombre) : usuarios.nombre != null) return false;
        if (apellido != null ? !apellido.equals(usuarios.apellido) : usuarios.apellido != null) return false;
        if (rol != null ? !rol.equals(usuarios.rol) : usuarios.rol != null) return false;
        if (correo != null ? !correo.equals(usuarios.correo) : usuarios.correo != null) return false;
        if (contrasena != null ? !contrasena.equals(usuarios.contrasena) : usuarios.contrasena != null) return false;
        if (avatar != null ? !avatar.equals(usuarios.avatar) : usuarios.avatar != null) return false;
        if (fechaInscripcion != null ? !fechaInscripcion.equals(usuarios.fechaInscripcion) : usuarios.fechaInscripcion != null)
            return false;
        if (historial != null ? !historial.equals(usuarios.historial) : usuarios.historial != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombre != null ? nombre.hashCode() : 0;
        result = 31 * result + (apellido != null ? apellido.hashCode() : 0);
        result = 31 * result + codi;
        result = 31 * result + (rol != null ? rol.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (contrasena != null ? contrasena.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (int) estado;
        result = 31 * result + (fechaInscripcion != null ? fechaInscripcion.hashCode() : 0);
        result = 31 * result + (historial != null ? historial.hashCode() : 0);
        return result;
    }

    public Collection<Puc> getPucsByCodi() {
        return pucsByCodi;
    }

    public void setPucsByCodi(Collection<Puc> pucsByCodi) {
        this.pucsByCodi = pucsByCodi;
    }
}
