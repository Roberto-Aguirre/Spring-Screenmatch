package com.aluracursos.screenmatch.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.modelos.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> getAllSeries() {
        return convertirDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convertirDatos(repository.findTop5ByOrderByEvaluacionDesc());
        

    }
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convertirDatos(repository.lanzamientosMasRecientes()); 
    }

    public List<SerieDTO> convertirDatos(List<Serie> listaSeries){
        return listaSeries.stream()
        .map(s -> new SerieDTO(s.getId(),
            s.getTitulo(), s.getTotaltemporadas(), s.getEvaluacion(), s.getGenero(),s.getSinopsis(), s.getCaratula(), s.getActores())
            )
        .collect(Collectors.toList());
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie =  repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(),s.getTitulo(), s.getTotaltemporadas(), s.getEvaluacion(), s.getGenero(),s.getSinopsis(), s.getCaratula(), s.getActores());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie =  repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
            .map(e->new EpisodioDTO(e.getTemporada(), e.getTitulo(),e.getNumeroEpisodio()))
            .collect(Collectors.toList());
        }
        return null;
    }

}
