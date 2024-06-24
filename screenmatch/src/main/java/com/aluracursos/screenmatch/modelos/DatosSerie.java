package com.aluracursos.screenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosSerie(@JsonAlias("Title")String titulo, @JsonAlias("totalSeasons")Integer totaltemporadas,@JsonAlias("imdbRating") String evaluacion) {

}
