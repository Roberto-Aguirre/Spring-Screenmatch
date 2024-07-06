package com.aluracursos.screenmatch.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double evaluaciones;
    private LocalDate fechaDeLanzamiento;
    @ManyToOne
    private Serie serie;

    public Episodio(){
        
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(Double evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Episodio(Integer temporada, DatosEpisodio d) {
        this.temporada = temporada;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try {
            this.evaluaciones = Double.parseDouble(d.evaluacion());
        } catch (NumberFormatException e) {
            this.evaluaciones = 0.0;
        }
        try {
            this.fechaDeLanzamiento = LocalDate.parse(d.fechaLanzamiento());

        } catch (DateTimeParseException e) {
            this.fechaDeLanzamiento = null;
        }
    }

    @Override
    public String toString() {
        return "Temporada: " + temporada +
                "| Titulo: " + titulo +
                "| numeroEpisodio: " + numeroEpisodio +
                "| evaluaciones " + evaluaciones +
                "| fechaDeLanzamiento " + fechaDeLanzamiento;
    }

}
