package com.tomasjuliano.literalura.principal;

import com.tomasjuliano.literalura.service.LiteraluraService;
import com.tomasjuliano.literalura.model.Libro;
import com.tomasjuliano.literalura.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {
    @Autowired
    private LiteraluraService service;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- Menú LiterAlura ---");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos en un año");
            System.out.println("5. Listar libros por idioma");
            System.out.println("0. Salir");
            System.out.print("\nSeleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el título a buscar y registrar: ");
                    String titulo = scanner.nextLine();
                    List<Libro> libros = service.buscarLibroPorTitulo(titulo);
                    if (!libros.isEmpty()) {
                        System.out.println("Se encontró el libro:");
                        libros.stream().findFirst().ifPresent(libro -> {
                            System.out.println("\n----------------------");
                            System.out.println("Título: " + libro.getTitulo());
                            System.out.println("Autores: " + libro.getAutores().stream().map(Autor::getNombre).toList());
                            System.out.println("Idioma: " + libro.getLenguaje());
                            System.out.println("Descargas: " + libro.getDescargas());
                            System.out.println("Resumen: " + libro.getResumen());
                            System.out.println("----------------------");
                        });
                    } else {
                        service.registrarLibroDesdeAPI(titulo);
                        libros = service.buscarLibroPorTitulo(titulo);
                        if (libros.isEmpty()) {
                            System.out.println("No se conoce la existencia del libro.");
                        } else {
                            System.out.println("Libro registrado exitosamente:");
                            libros.stream().findFirst().ifPresent(libro -> {
                                System.out.println("\n----------------------");
                                System.out.println("Título: " + libro.getTitulo());
                                System.out.println("Autores: " + libro.getAutores().stream().map(Autor::getNombre).toList());
                                System.out.println("Idioma: " + libro.getLenguaje());
                                System.out.println("Descargas: " + libro.getDescargas());
                                System.out.println("Resumen: " + libro.getResumen());
                                System.out.println("----------------------");
                            });
                        }
                    }
                }
                case 2 -> {
                    List<Libro> libros = service.listarLibros();
                    if (libros.isEmpty()) {
                        System.out.println("\nNo hay libros registrados.");
                    } else {
                        libros.forEach(libro -> {
                            System.out.println("\n----------------------");
                            System.out.println("Título: " + libro.getTitulo());
                            System.out.println("Autores: " + libro.getAutores().stream().map(Autor::getNombre).toList());
                            System.out.println("Idioma: " + libro.getLenguaje());
                            System.out.println("Descargas: " + libro.getDescargas());
                            System.out.println("Resumen: " + libro.getResumen());
                            System.out.println("----------------------");
                        });
                    }
                }
                case 3 -> {
                    List<Autor> autores = service.listarAutores();
                    if (autores.isEmpty()) {
                        System.out.println("\nNo hay autores registrados.");
                    } else {
                        autores.forEach(autor -> {
                            System.out.println("\n----------------------");
                            System.out.println("Nombre: " + autor.getNombre());
                            System.out.println("Año de nacimiento: " + autor.getAnioNacimiento());
                            System.out.println("Año de fallecimiento: " + autor.getAnioFallecimiento());
                            System.out.println("----------------------");
                        });
                    }
                }
                case 4 -> {
                    System.out.print("\nIngrese el año: ");
                    String anio = scanner.nextLine();
                    List<Autor> autores = service.listarAutoresVivosEnAnio(anio);
                    if (autores.isEmpty()) {
                        System.out.println("\nNo se encontraron autores vivos en ese año.");
                    } else {
                        autores.forEach(autor -> {
                            System.out.println("\n----------------------");
                            System.out.println("Nombre: " + autor.getNombre());
                            System.out.println("Año de nacimiento: " + autor.getAnioNacimiento());
                            System.out.println("Año de fallecimiento: " + autor.getAnioFallecimiento());
                            System.out.println("----------------------");
                        });
                    }
                }
                case 5 -> {
                    System.out.print("\nIngrese el idioma: ");
                    String idioma = scanner.nextLine();
                    List<Libro> libros = service.listarLibrosPorIdioma(idioma);
                    if (libros.isEmpty()) {
                        System.out.println("\nNo se encontraron libros en ese idioma.");
                    } else {
                        libros.forEach(libro -> {
                            System.out.println("\n----------------------");
                            System.out.println("Título: " + libro.getTitulo());
                            System.out.println("Autores: " + libro.getAutores().stream().map(Autor::getNombre).toList());
                            System.out.println("Idioma: " + libro.getLenguaje());
                            System.out.println("Descargas: " + libro.getDescargas());
                            System.out.println("Resumen: " + libro.getResumen());
                            System.out.println("----------------------");
                        });
                    }
                }
                case 0 -> System.out.println("\n¡Hasta luego!");
                default -> System.out.println("\nOpción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
}
