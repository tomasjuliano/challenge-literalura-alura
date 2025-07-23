package com.tomasjuliano.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosLibro(
        @JsonAlias("id") String id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<Autor> autores,
        @JsonAlias("languages") List<String> lenguajes,
        @JsonAlias("summaries") List<String> resumenes,
        @JsonAlias("download_count") Integer descargas){

    @Override
    public String toString() {
        return  "\n_____________________________________" +
                "\nID: " + id +
                "\nTítulo: " + titulo +
                "\nAutores: " + autores +
                "\nLenguajes: " + lenguajes +
                "\nResumenes: " + resumenes +
                "\nDescargas: " + descargas +
                "\n_____________________________________";
    }
}