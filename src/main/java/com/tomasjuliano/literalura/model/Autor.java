package com.tomasjuliano.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String anioNacimiento;
    private String anioFallecimiento;

    // Constructor vacío requerido por JPA
    public Autor() {}

    public Autor(String nombre, String anioNacimiento, String anioFallecimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnioNacimiento() {
        return anioNacimiento;
    }
    public void setAnioNacimiento(String anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public String getAnioFallecimiento() {
        return anioFallecimiento;
    }
    public void setAnioFallecimiento(String anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }
}