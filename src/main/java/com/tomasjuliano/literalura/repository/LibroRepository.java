package com.tomasjuliano.literalura.repository;

import com.tomasjuliano.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByLenguajeContaining(String lenguaje);
}
