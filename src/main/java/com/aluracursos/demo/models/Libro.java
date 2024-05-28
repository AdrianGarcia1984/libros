package com.aluracursos.demo.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name ="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    private List<String> languages;
    private Double download_count;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> authors;

    public Libro (DatosLibros datosLibros){
        this.title=datosLibros.title();
        this.languages=datosLibros.languages();
        this.download_count= datosLibros.download_count();
        this.authors = datosLibros.authors().stream()
                        .map(e->new Autor(e))
                .collect(Collectors.toList());;

    }

    public Libro() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<Autor> getauthors() {
        return authors;
    }

    public void setauthors(List<Autor> Autor) {
        Autor.forEach(e->e.setLibro(this));
        this.authors = Autor;
    }

    public Double getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Double numeroDescargas) {
        this.download_count = numeroDescargas;
    }
}
