package com.aluracursos.demo.repository;

import com.aluracursos.demo.models.Autor;
import com.aluracursos.demo.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitle(String title);

    @Query("SELECT s FROM Autor s")
    List<Autor> findAuthors();

    //@Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numerotemporada")
    @Query("SELECT s FROM Autor s WHERE s.birth_date = :year")
    List<Autor> findAuthorsForYear(Long year);

    @Query("SELECT s FROM Libro s WHERE s.languages = :idioma")
    List<Libro> findBookForLang(List<String>  idioma);
}
