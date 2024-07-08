package com.aluracursos.screenmatch.dto;

import com.aluracursos.screenmatch.modelos.Genero;

public record SerieDTO(
Long id,
String titulo,
Integer totaltemporadas,
Double evaluacion,
Genero genero,
String sinopsis,
String caratula,
String actores)
{

}
