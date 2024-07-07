package com.aluracursos.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import com.aluracursos.screenmatch.modelos.DatosSerie;
import com.aluracursos.screenmatch.modelos.DatosTemporada;
import com.aluracursos.screenmatch.modelos.Episodio;
import com.aluracursos.screenmatch.modelos.Genero;
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
            \n1 - Buscar series
            2 - Buscar episodios
            3 - Ver series buscadas
            4 - Buscar series por titulo
            5 - Top 5 mejores series
            6 - Series por categoria
            7 - Series por calificacion y temporadas.
            8 - Buscar episodios por titulo

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
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 7:
                    buscarSeriesPorTemporadasAndEvaluacion();
                    break;
                case 8:
                    buscarEpisodioPorTitulo();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                default:
                    System.out.println("Valor invalido, por favor ingreselo de nuevo.");
            }
        }
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

    private void buscarTop5Series() {
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(e -> System.out.println("Serie: " + e.getTitulo() + " Evaluacion: " + e.getEvaluacion()));

    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Escriba el genero/categoria a buscar: ");
        var genero = teclado.nextLine();
        var categoria = Genero.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series de las categorias " + genero);
        seriesPorCategoria.forEach(e -> System.out.println(e.getTitulo()));
    }

    private void buscarSeriesPorTemporadasAndEvaluacion() {
        System.out.println("Escriba el numero de temporadas que contenga la series: ");
        var temporadas = teclado.nextLine();
        System.out.println("Escriba la calificacion minima que contenga la serie: ");
        var evaluacion = teclado.nextLine();
        List<Serie> seriesPorEvaluacion = repositorio.seriesPorTemporadasYEvaluacion(Integer.valueOf(temporadas),
                Double.valueOf(evaluacion));
        System.out.println("\nLas series con las siguiente temporadas: " + evaluacion + " y evaluaciones: " + evaluacion
                + " son: ");
        seriesPorEvaluacion.forEach(e -> System.out.println("Titulo: " + e.getTitulo() + " Temporadas: "
                + e.getTotaltemporadas() + " Evaluacion: " + e.getEvaluacion()));

    }

    private void buscarEpisodioPorTitulo() {
        System.out.println("Ingresa el nombre del episodio a buscar");
        var nombreEpisodio = teclado.nextLine();

        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        // System.out.println(episodiosEncontrados);
        episodiosEncontrados.forEach(e -> System.out.printf(
                "Serie: %s | Titulo: %s | Capitulo No: %s | Evaluacion: %s | ",
                e.getSerie().getTitulo(), e.getTitulo(), e.getNumeroEpisodio(), e.getEvaluaciones()));

    }

    // private void buscarSeriesPorTemporadas(){
    // System.out.println("Escriba las temporadas para buscar la serie: ");
    // var temporadas = teclado.nextLine();
    // List<Serie> seriesPorTemporadas =
    // repositorio.findByTotaltemporadas(Integer.valueOf(temporadas));
    // System.out.println("\nLas series con las siguientes "+ temporadas+ "
    // temporadas son: ");
    // seriesPorTemporadas.forEach(e->System.out.println(e.getTitulo()));
    // }
}
