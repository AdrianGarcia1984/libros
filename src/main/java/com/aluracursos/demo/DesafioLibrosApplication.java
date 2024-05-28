package com.aluracursos.demo;

import com.aluracursos.demo.main.main;
import com.aluracursos.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioLibrosApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DesafioLibrosApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		var princpal = new main(repository);
		princpal.muestraElMenu();

	}

}
