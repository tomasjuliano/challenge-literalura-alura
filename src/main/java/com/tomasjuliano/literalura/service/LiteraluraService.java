package com.tomasjuliano.literalura.service;

import com.tomasjuliano.literalura.model.Libro;
import com.tomasjuliano.literalura.model.Autor;
import com.tomasjuliano.literalura.repository.LibroRepository;
import com.tomasjuliano.literalura.repository.AutorRepository;
import com.tomasjuliano.literalura.service.ConsumoAPI;
import com.tomasjuliano.literalura.service.ConvierteDatos;
import com.tomasjuliano.literalura.model.DatosLibro;
import com.tomasjuliano.literalura.model.DatosAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LiteraluraService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConvierteDatos convierteDatos;

    // 1. Buscar libro por título
    public List<Libro> buscarLibroPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // 2. Listar libros registrados
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    // 3. Listar autores registrados
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    // 4. Listar autores vivos en un determinado año
    public List<Autor> listarAutoresVivosEnAnio(String anio) {
        return autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio);
    }

    // 5. Listar libros por idioma
    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByLenguajeContaining(idioma);
    }

    // Método para registrar un libro y sus autores desde la API Gutendex
    public void registrarLibroDesdeAPI(String titulo) {
        String tituloCodificado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + tituloCodificado;
        String json = consumoAPI.obtenerDatos(url);
        // Extraer el primer libro de la lista 'results' del JSON
        try {
            var node = new com.fasterxml.jackson.databind.ObjectMapper().readTree(json);
            var results = node.get("results");
            if (results != null && results.isArray() && results.size() > 0) {
                var libroNode = results.get(0);
                DatosLibro datosLibro = convierteDatos.obtenerDatos(libroNode.toString(), DatosLibro.class);
                // Verificar si el libro ya está registrado por título
                List<Libro> librosExistentes = libroRepository.findByTituloContainingIgnoreCase(datosLibro.titulo());
                if (!librosExistentes.isEmpty()) {
                    System.out.println("El libro ya está registrado en la base de datos.");
                    return;
                }
                List<Autor> autores = datosLibro.autores().stream()
                    .map(da -> new Autor(
                        da.nombre(),
                        da.anioNacimiento() != null ? da.anioNacimiento().toString() : "Desconocido",
                        da.anioFallecimiento() != null ? da.anioFallecimiento().toString() : "Desconocido"
                    ))
                    .toList();
                if (!autores.isEmpty()) {
                    for (Autor autor : autores) {
                        if (autorRepository.findByNombreContainingIgnoreCase(autor.getNombre()).isEmpty()) {
                            autorRepository.save(autor);
                        }
                    }
                }
                List<Autor> autoresPersistidos = autores.stream()
                    .map(autor -> {
                        List<Autor> existentes = autorRepository.findByNombreContainingIgnoreCase(autor.getNombre());
                        if (existentes.isEmpty()) {
                            return autorRepository.save(autor);
                        } else {
                            return existentes.get(0);
                        }
                    })
                    .toList();
                // Tomar el primer resumen y lenguaje si existen, sino dejar vacío
                String resumen = (datosLibro.resumenes() != null && !datosLibro.resumenes().isEmpty()) ? datosLibro.resumenes().get(0) : "";
                String lenguaje = (datosLibro.lenguajes() != null && !datosLibro.lenguajes().isEmpty()) ? datosLibro.lenguajes().get(0) : "";
                Libro libro = new Libro(
                    datosLibro.titulo(),
                    autoresPersistidos,
                    resumen,
                    lenguaje,
                    datosLibro.descargas() != null ? datosLibro.descargas() : 0
                );
                libroRepository.save(libro);
            }
        } catch (Exception e) {
            System.out.println("Error procesando datos del libro: " + e.getMessage());
        }
    }
}
