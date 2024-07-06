package com.aluracursos.screenmatch.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

import com.aluracursos.screenmatch.modelos.DatosEpisodio;
import com.aluracursos.screenmatch.modelos.DatosSerie;
import com.aluracursos.screenmatch.modelos.DatosTemporada;
import com.aluracursos.screenmatch.modelos.Episodio;
import com.aluracursos.screenmatch.modelos.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.services.ConsumoAPI;
import com.aluracursos.screenmatch.services.ConvierteDatos;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "http://omdbapi.com/?";
    private final String API_KEY = "apikey=57bedfc9&t=";
    private ConvierteDatos conversor = new ConvierteDatos();
    // private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;
    private String menu = """
            1 - Buscar series
            2 - Buscar episodios
            3 - Ver series buscadas
            4 - Buscar series por titulo
            0 - Salir
            """;

    public Principal(SerieRepository serieRepository) {
        this.repositorio = serieRepository;
    }

    public void mostrarMenu() {
        // Menu
        var opcion = -1;

        while (opcion != 0) {

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                default:
                    System.out.println("Valor invalido, por favor ingreselo de nuevo.");
            }
        }

        // Ingreso de Input

        // Inicializar
        //

        // // temporadas.forEach(System.out::println);
        // System.out.println(datos.totaltemporadas());
        // System.out.println(temporadas.size());
        // temporadas.forEach(t -> t.episodios().forEach(e ->
        // System.out.println(e.titulo())));
        // List<DatosEpisodio> datosEpisodio = temporadas.stream()
        // .flatMap(t -> t.episodios().stream())
        // .collect(Collectors.toList());

        // // Top 5 episodios
        // datosEpisodio.stream()
        // .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
        // .peek(e -> System.out.println("Filtro (N/A) " + e))
        // .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
        // .peek(e -> System.out.println("Filtro Ordenacion " + e))
        // .map(e -> e.titulo().toUpperCase())
        // .peek(e -> System.out.println("Filtro mayusculas " + e))
        // .limit(5)
        // // .peek(e->System.out.println("Filtro Limitaciones "+ e))
        // .forEach(e -> System.out.println(e));
        // // Convirtiendo los datos a una lista de tipo episodio.
        // List<Episodio> episodios = temporadas.stream()
        // .flatMap(t -> t.episodios().stream()
        // .map(d -> new Episodio(t.numeroTemporada(), d)))
        // .collect(Collectors.toList());

        // episodios.forEach(e->System.out.println(e));

        // ! Seccion de buscar por fecha
        // Busqueda de episodios a partir de x año

        // System.out.println("Ingresa el año a buscar episodios");
        // var fecha = teclado.nextInt();
        // teclado.nextLine();

        // LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // episodios.stream()
        // .filter(e->e.getFechaDeLanzamiento()!=null&&
        // e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
        // .forEach(e->System.out.println(
        // "Temporada "+ e.getTemporada()+
        // " Titulo: " + e.getTitulo()+
        // " Fecha: "+e.getFechaDeLanzamiento().format(dtf)
        // ));

        // #region
        // System.out.println("Escriba el episodio a revisar: ");
        // var tituloSerie = teclado.nextLine();

        // Optional<Episodio> episodioBuscado = episodios.stream()
        // .filter(e->e.getTitulo().toUpperCase().contains(tituloSerie.toUpperCase()))
        // .findFirst();
        // if (episodioBuscado.isPresent()) {
        // System.out.println("Episodio encontrado");
        // System.out.println("Los datos son: ");
        // System.out.println(episodioBuscado.get().getTitulo());
        // } else {
        // System.out.println("Episodio no encontrado");
        // }
        // #endregion

        // Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
        // .filter(e -> e.getEvaluaciones() > 0.0)
        // .collect(Collectors.groupingBy(Episodio::getTemporada,
        // Collectors.averagingDouble(Episodio::getEvaluaciones)));

        // System.out.println(evaluacionesPorTemporada);
        // DoubleSummaryStatistics est = episodios.stream()
        // .filter(e -> e.getEvaluaciones() > 0.0)
        // .collect(Collectors.summarizingDouble(Episodio::getEvaluaciones));
        // System.out.println("Media de evaluaciones: " + est.getAverage());
        // System.out.println("Evaluacion mas alta: " + est.getMax());
        // System.out.println("Evaluacion mas baja: " + est.getMin());
        // System.out.println("Episodios evaluados: " + est.getCount());
    }

    public DatosSerie getDatosSerie() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var nombreSerieFix = nombreSerie.replace(" ", "+");
        // Busca los datos de las temporadas.
        var json = consumoAPI.obtenerDatos(URL_BASE + API_KEY + nombreSerieFix);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        // System.out.println(datos);
        return datos;
    }

    public void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la series de la cual quieres ver sus episodios: ");
        var nombreSerie = teclado.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DatosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotaltemporadas(); i++) {

                var json = consumoAPI.obtenerDatos(
                        URL_BASE + API_KEY + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i);
                // System.out.println(temporada);
                DatosTemporada datosTemporada = conversor.obtenerDatos(json,
                        DatosTemporada.class);
                temporadas.add(datosTemporada);
            }

            temporadas.forEach(e -> System.out.println(e + "\n"));
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(s -> s.episodios().stream()
                            .map(e -> new Episodio(s.numeroTemporada(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {

        }
        // DatosSerie datosSerie = getDatosSerie();
        // List<DatosTemporada> temporadas = new ArrayList<>();

        // for (int i = 1; i <= datosSerie.totaltemporadas(); i++) {

        // var json = consumoAPI
        // .obtenerDatos(URL_BASE + API_KEY + datosSerie.titulo().replace(" ", "+") +
        // "&Season=" + i);
        // // System.out.println(temporada);
        // DatosTemporada datosTemporada = conversor.obtenerDatos(json,
        // DatosTemporada.class);
        // temporadas.add(datosTemporada);
        // }

        // temporadas.forEach(System.out::println);
    }

    public void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        // ? datosSeries.add(datos);
        // System.out.println(datos);
    }

    public void mostrarSeriesBuscadas() {
        series = repositorio.findAll();
        series.forEach(e -> System.out.println(e + "\n"));
        // List<Serie> series = new ArrayList<>();
        // series = datosSeries.stream()
        // .map(d -> new Serie(d))
        // .collect(Collectors.toList());

        // series.stream()
        // .sorted(Comparator.comparing(Serie::getGenero))
        // .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la series a buscar");
        var nombreSerie = teclado.nextLine();

        Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent()) {
            System.out.println("La serie buscada es: " + serieBuscada.get());
        } else {
            System.out.println("Serie no encontrada");
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
