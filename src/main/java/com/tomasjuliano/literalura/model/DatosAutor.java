package com.tomasjuliano.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer anioNacimiento,
        @JsonAlias("death_year") Integer anioFallecimiento) {

    @Override
    public String toString() {
        return  "\n_____________________________________" +
                "\nNombre: " + nombre +
                "\nAño de Nacimiento: " + (anioNacimiento != null ? anioNacimiento : "Desconocido") +
                "\nAño de Fallecimiento: " + (anioFallecimiento != null ? anioFallecimiento : "Desconocido") +
                "\n_____________________________________";
    }
}