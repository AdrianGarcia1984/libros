package com.aluracursos.demo.main;

import com.aluracursos.demo.models.Autor;
import com.aluracursos.demo.models.Datos;
import com.aluracursos.demo.models.DatosLibros;
import com.aluracursos.demo.models.Libro;
import com.aluracursos.demo.repository.LibroRepository;
import com.aluracursos.demo.services.ConsumoApi;
import com.aluracursos.demo.services.ConvierteDatos;

import java.util.*;

public class main {

    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<Libro> libros;
    private List<Autor> Autores;
    private List<String> idiomas;


    public main(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros
                    2 - listar libros buscados
                    3 - listar autores registrados
                    4 - listar autores por año de nacimiento
                    5 - listar libros por idioma
                                                      
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    muestraLibro();
                    break;
                case 2:
                    ListarLibrosBuscados();
                    break;
                case 3:
                    ListarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresEnDeterminadoAño();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                presione la opcion correcta:
                 1- ingles
                 2- español
                 3- frances
                 4- ruso
                 5- aleman
                """);
        int numero = sc.nextInt();
        sc.nextLine();
        var idioma = "";
        switch (numero) {
            case 1:
                idioma = "en";
                break;
            case 2:
                idioma = "es";
                break;
            case 3:
                idioma = "fr";
                break;
            case 4:
                idioma = "ru";
                break;
            case 5:
                idioma = "de";
                break;
            default:
                System.out.println("Opción inválida");
        }
        idiomas = new ArrayList<>();
        idiomas.add(idioma);
        libros = repositorio.findBookForLang(idiomas);
        System.out.println(libros.size());
        if (libros.size() != 0) {
            System.out.println("listado de libros por idioma");
            libros.stream()
                    .forEach(s ->
                            System.out.printf("Libro: " + s.getTitle() + "\n")
                    );
        } else {
            System.out.println("no hay ningun libro guardado en la base de datos con ese idioma");
        }
    }

    private void listarAutoresEnDeterminadoAño() {
        System.out.println("ingrese el año a buscar: ");
        var year = sc.nextLong();
        Autores = repositorio.findAuthorsForYear(year);
        Autores.stream().forEach(a -> System.out.printf(
                "autor: " + a.getName() + "\n" +
                        "nacimiento: " + a.getBirth_date() + "\n" +
                        "muerte: " + a.getDeath_date() + "\n"

        ));
    }


    public void muestraLibro() {
        System.out.println("ingrese parte del nombre: ");
        var titulo = sc.nextLine();
        var json = consumoApi.obtenerDatos(URL + "?search=" + titulo.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> librosBuscado = datosBusqueda.libros().stream()
                .filter(l -> l.title().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();
        if (librosBuscado.isPresent()) {
            Libro libro = new Libro(librosBuscado.get());
            Optional<Libro> libro2 = repositorio.findByTitle(librosBuscado.get().title());
            if (libro2.isPresent()) {
                System.out.println("libro encontrado en la base de datos");
                System.out.println("nombre: " + libro2.get().getTitle());
                System.out.println("descargas: " + libro2.get().getDownload_count());

            } else {
                repositorio.save(libro);
                System.out.println("libro encontrado y guardado en la base de datos");
                System.out.println("nombre: " + libro.getTitle());
                ;
                System.out.println("descargas: " + libro.getDownload_count());
            }

        } else {
            System.out.println("libro no encotrado");
        }
    }


    public void ListarLibrosBuscados() {
        libros = repositorio.findAll();
        libros.stream()
                .forEach(s -> System.out.printf("Libro: " + s.getTitle() + "\n")
                );
    }


    public void ListarAutoresRegistrados() {
        System.out.println("Listado de autores");
        Autores = repositorio.findAuthors();
        Autores.stream().forEach(a -> System.out.println(
                "autor: " + a.getName() + " nacimiento: " + a.getBirth_date()

        ));
    }
}

