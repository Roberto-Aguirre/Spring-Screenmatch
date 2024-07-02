package com.aluracursos.screenmatch.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import java.util.stream.Collectors;

import com.aluracursos.screenmatch.modelos.DatosEpisodio;
import com.aluracursos.screenmatch.modelos.DatosSerie;
import com.aluracursos.screenmatch.modelos.DatosTemporada;
import com.aluracursos.screenmatch.modelos.Episodio;
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
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        List<DatosEpisodio> datosEpisodio = temporadas.stream()
            .flatMap(t->t.episodios().stream())
            .collect(Collectors.toList());

            //Top 5 episodios
            datosEpisodio.stream()
            .filter(e->!e.evaluacion().equalsIgnoreCase("N/A"))
            .peek(e->System.out.println("Filtro (N/A) "+ e))
            .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
            .peek(e->System.out.println("Filtro Ordenacion "+ e))
            .map(e->e.titulo().toUpperCase())
            .peek(e->System.out.println("Filtro mayusculas "+ e))
            .limit(5)
            // .peek(e->System.out.println("Filtro Limitaciones "+ e))
            .forEach(e->System.out.println(e));
        //Convirtiendo los datos a una lista de tipo episodio.
        List<Episodio> episodios = temporadas.stream()
        .flatMap(t->t.episodios().stream()
        .map(d->new Episodio(t.numeroTemporada(),d))
        )
        .collect(Collectors.toList());
        


        // episodios.forEach(e->System.out.println(e));

        //! Seccion de buscar por fecha
        //Busqueda de episodios a partir de x año

        // System.out.println("Ingresa el año a buscar episodios");
        // var fecha = teclado.nextInt();
        // teclado.nextLine();
        
        // LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // episodios.stream()
        // .filter(e->e.getFechaDeLanzamiento()!=null&& e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
        // .forEach(e->System.out.println(
        //     "Temporada "+ e.getTemporada()+
        //     " Titulo: " + e.getTitulo()+
        //     " Fecha: "+e.getFechaDeLanzamiento().format(dtf) 
        //     ));


        System.out.println("Escriba el episodio a revisar: ");
        var tituloSerie = teclado.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream() 
            .filter(e->e.getTitulo().toUpperCase().contains(tituloSerie.toUpperCase()))
            .findFirst();
        if (episodioBuscado.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Los datos son: ");
            System.out.println(episodioBuscado.get().getTitulo());
        } else {
            System.out.println("Episodio no encontrado");
        }
        
    }

}

// for (int i = 0; i < datos.totaltemporadas(); i++) {
// List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
// System.out.println("Temporada: "+temporadas.get(i).numeroTemporada());

// for (int j = 0; j < episodiosTemporada.size(); j++) {
// System.out.println(episodiosTemporada.get(j).titulo());
// // DEBUG
// // System.out.println(i + " " + j);
// }
// }
