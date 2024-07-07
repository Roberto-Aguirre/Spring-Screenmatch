package com.aluracursos.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluracursos.screenmatch.modelos.Genero;
import com.aluracursos.screenmatch.modelos.Serie;
import java.util.List;
import java.util.Optional;


public interface SerieRepository extends JpaRepository<Serie,Long>{
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Genero genero);
    List<Serie> findByEvaluacion(Double evaluacion);
    List<Serie> findByTotaltemporadas(Integer totaltemporadas);

    
}
