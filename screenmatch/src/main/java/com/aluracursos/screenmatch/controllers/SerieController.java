package com.aluracursos.screenmatch.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/series")

public class SerieController {
    @Autowired
    private SerieService serieService;

    @GetMapping()
    public List<SerieDTO> getAllSeries() {
        return serieService.getAllSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5() {
        return serieService.obtenerTop5();
    }
    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return serieService.obtenerLanzamientosMasRecientes();
    }
}
