package com.aluracursos.screenmatch.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.repository.SerieRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SerieController {
    @Autowired
    private SerieRepository repository;

    @GetMapping("/series")
    public List<SerieDTO> getAllSeries() {
        return repository.findAll().stream()
        .map(s->new SerieDTO(s.getTitulo(), s.getTotaltemporadas(), s.getEvaluacion(), s.getGenero(), s.getSinopsis(), s.getCaratula(), s.getActores()))
        .collect(Collectors.toList());
    }

}
