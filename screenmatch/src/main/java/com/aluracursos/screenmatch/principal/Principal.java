package com.aluracursos.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.aluracursos.screenmatch.modelos.DatosEpisodio;
import com.aluracursos.screenmatch.modelos.DatosSerie;
import com.aluracursos.screenmatch.modelos.DatosTemporada;
import com.aluracursos.screenmatch.services.ConsumoAPI;
import com.aluracursos.screenmatch.services.ConvierteDatos;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "http://omdbapi.com/?";
    private final String API_KEY = "apikey=57bedfc9&t=";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void mostrarMenu() {
        // Ingreso de Input
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var nombreSerieFix = nombreSerie.replace(" ", "+");
        // Busca los datos de las temporadas.
        var json = consumoAPI.obtenerDatos(URL_BASE + API_KEY + nombreSerieFix);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        // Inicializar
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totaltemporadas(); i++) {

            var temporada = consumoAPI.obtenerDatos(URL_BASE + API_KEY + nombreSerieFix + "&Season=" + i);
            // System.out.println(temporada);
            var datosTemporada = conversor.obtenerDatos(temporada, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }

        // temporadas.forEach(System.out::println);
        System.out.println(datos.totaltemporadas());
        System.out.println(temporadas.size());

        // for (int i = 0; i < datos.totaltemporadas(); i++) {
        // List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
        // System.out.println("Temporada: "+temporadas.get(i).numeroTemporada());

        // for (int j = 0; j < episodiosTemporada.size(); j++) {
        // System.out.println(episodiosTemporada.get(j).titulo());
        // // DEBUG
        // // System.out.println(i + " " + j);
        // }
        // }
            
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

    }

}
