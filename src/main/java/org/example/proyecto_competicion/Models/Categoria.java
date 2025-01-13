package org.example.proyecto_competicion.Models;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Categoria {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "categoriaByIdCategoria")
    private Collection<Competicion> competicionsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        if (id != categoria.id) return false;
        if (nombre != null ? !nombre.equals(categoria.nombre) : categoria.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    public Collection<Competicion> getCompeticionsById() {
        return competicionsById;
    }

    public void setCompeticionsById(Collection<Competicion> competicionsById) {
        this.competicionsById = competicionsById;
    }
}
