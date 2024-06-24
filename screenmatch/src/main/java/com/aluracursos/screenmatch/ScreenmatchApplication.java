package com.aluracursos.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.screenmatch.services.ConsumoAPI;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obtenerDatos("http://omdbapi.com/?apikey=57bedfc9&t=game+of+thrones");
		// var json = consumoAPI.obtenerDatos("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		
		// throw new UnsupportedOperationException("Unimplemented method 'run'");
	}

}
