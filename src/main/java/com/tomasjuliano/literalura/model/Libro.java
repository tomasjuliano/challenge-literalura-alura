package com.tomasjuliano.literalura.model;

public class Libro{
    private int id;
    private String titulo;
    private List<Autor> autores;
    private List<String> resumenes;
    private List<String> lenguajes;
    private int descargas;

    public Libro(int id, String titulo, List<Autor> autores, List<String> resumenes, List<String> lenguajes, int descargas) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.resumenes = resumenes;
        this.lenguajes = lenguajes;
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

    public List<String> getResumenes() {
        return resumenes;
    }
    public void setResumenes(List<String> resumenes) {
        this.resumenes = resumenes;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }
    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = lenguajes;
    }

    public int getDescargas() {
        return descargas;
    }
    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }
}