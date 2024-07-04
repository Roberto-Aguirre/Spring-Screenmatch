package com.aluracursos.screenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosSerie(
        @JsonAlias("Title") String titulo, 
        @JsonAlias("totalSeasons") Integer totaltemporadas,
        @JsonAlias("imdbRating") String evaluacion, 
        @JsonAlias("Genre") String genero,
        @JsonAlias("Plot") String sinopsis,
        @JsonAlias("Poster") String caratula,
        @JsonAlias("Actors") String actores
        ) {

}
