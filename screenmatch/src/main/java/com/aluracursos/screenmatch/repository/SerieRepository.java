package com.aluracursos.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aluracursos.screenmatch.modelos.Episodio;
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

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluaciones DESC LIMIT 5")
    List<Episodio> top5MejorEpisodios(Serie serie);
    
    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();
}
