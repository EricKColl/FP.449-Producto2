package edu.uoc.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Autor de uno o varios libros. Lado inverso de la relación N:M con {@link Libro}.
 */
@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 120, message = "Los apellidos no pueden superar los 120 caracteres")
    @Column(nullable = false, length = 120)
    private String apellidos;

    @Size(max = 60, message = "La nacionalidad no puede superar los 60 caracteres")
    @Column(length = 60)
    private String nacionalidad;

    // Formato de <input type="date"> en los formularios
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    @ManyToMany(mappedBy = "autores")
    @OrderBy("titulo ASC")
    private Set<Libro> libros = new LinkedHashSet<>();

    public Autor() {
    }

    public Autor(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    // Igualdad por identificador: necesaria para usar entidades dentro de un Set
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autor otro)) {
            return false;
        }
        return id != null && id.equals(otro.getId());
    }

    @Override
    public int hashCode() {
        return Autor.class.hashCode();
    }

    @Override
    public String toString() {
        return "Autor{id=" + id + ", nombre='" + getNombreCompleto() + "'}";
    }
}
