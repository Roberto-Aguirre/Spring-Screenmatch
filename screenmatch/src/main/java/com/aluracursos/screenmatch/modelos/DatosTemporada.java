package com.aluracursos.screenmatch.modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTemporada(@JsonAlias("Season")Integer numeroTemporada,@JsonAlias("Episodes")List<DatosEpisodio> episodios) {

}
