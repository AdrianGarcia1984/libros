package com.aluracursos.demo.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name ="autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String birth_date;
    private String death_date;
    @ManyToOne
    private Libro libro;

    public Autor(DatosAutor autor) {
        this.name=autor.name();
        this.birth_date=autor.birth_year();
        this.death_date=autor.death_year();
    }

    public Autor() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getDeath_date() {
        return death_date;
    }

    public void setDeath_date(String death_date) {
        this.death_date = death_date;
    }

    public com.aluracursos.demo.models.Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return  "Id=" + Id +
                ", name='" + name + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", death_date='" + death_date;
    }
}
