package com.tomasjuliano.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libro")
public class Libro{
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Genera los ID autoincrementalmente.
    private int id;
    private String titulo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "libro_autor",
        joinColumns = @JoinColumn(name = "libro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    // Elimino las colecciones y uso campos simples para idioma y resumen
    @Column(length = 2000)
    private String resumen;
    private String lenguaje;
    private int descargas;

    // Constructor predeterminado requerido por JPA.
    public Libro() {
        // Constructor por defecto
    }

    // Constructor sin id para JPA
    public Libro(String titulo, List<Autor> autores, String resumen, String lenguaje, int descargas) {
        this.titulo = titulo;
        this.autores = autores;
        this.resumen = resumen;
        this.lenguaje = lenguaje;
        this.descargas = descargas;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }
    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getResumen() {
        return resumen;
    }
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
    public String getLenguaje() {
        return lenguaje;
    }
    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public int getDescargas() {
        return descargas;
    }
    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }
}