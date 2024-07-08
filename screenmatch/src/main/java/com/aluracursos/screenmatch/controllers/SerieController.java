package com.aluracursos.screenmatch.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class SerieController {
@GetMapping("/series")
public String mostrarMensaje() {
    return new String("Hola mundo");
}

}
