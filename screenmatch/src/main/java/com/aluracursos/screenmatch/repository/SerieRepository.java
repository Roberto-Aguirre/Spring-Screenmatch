package com.aluracursos.screenmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluracursos.screenmatch.modelos.Serie;
import java.util.List;
import java.util.Optional;


public interface SerieRepository extends JpaRepository<Serie,Long>{
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

}
