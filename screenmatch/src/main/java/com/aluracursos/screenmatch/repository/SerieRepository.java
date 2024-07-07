package com.aluracursos.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aluracursos.screenmatch.modelos.Genero;
import com.aluracursos.screenmatch.modelos.Serie;
import java.util.List;
import java.util.Optional;


public interface SerieRepository extends JpaRepository<Serie,Long>{
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Genero genero);
    List<Serie> findByTotaltemporadasLessThanEqualAndEvaluacionGreaterThanEqual(Integer totaltemporadas,double evluacion);
    // @Query(value="SELECT * FROM series WHERE totaltemporadas <= 2 AND evaluacion >= 7",nativeQuery = true)
    @Query("SELECT s FROM Serie s WHERE s.totaltemporadas <= :temporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadasYEvaluacion(int temporadas, Double evaluacion);

    
}
