package com.aluracursos.screenmatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.repository.SerieRepository;


@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{
	@Autowired
	private SerieRepository serieRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	Principal principal = new Principal(serieRepository);
	principal.mostrarMenu();
	}

	
}
/* 
Antiguo Main
		//var consumoAPI = new ConsumoAPI();
		// var serie = consumoAPI.obtenerDatos("http://omdbapi.com/?apikey=57bedfc9&t=game+of+thrones");
		// var episodio = consumoAPI.obtenerDatos("http://omdbapi.com/?apikey=57bedfc9&t=game+of+thrones&Season=1&Episode=1");
		// var temporada = consumoAPI.obtenerDatos("http://omdbapi.com/?apikey=57bedfc9&t=game+of+thrones&Season=1");
		// var json = consumoAPI.obtenerDatos("https://coffee.alexflipnote.dev/random.json");
		// System.out.println(serie);
		// ConvierteDatos conversor = new ConvierteDatos();
		// var datos = conversor.obtenerDatos(serie, DatosSerie.class);
		// System.out.println(datos);
		
		// var episodioDatos = conversor.obtenerDatos(episodio, DatosEpisodio.class);
		// System.out.println(episodioDatos);
		
		
		// var temporadaDatos = conversor.obtenerDatos(temporada, DatosTemporada.class);
		// System.out.println(temporadaDatos);

		// throw new UnsupportedOperationException("Unimplemented method 'run'");
 */