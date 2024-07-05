package com.aluracursos.screenmatch.modelos;

import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;

    private Integer totaltemporadas;

    private Double evaluacion;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private String sinopsis;

    private String caratula;

    private String actores;

    @Transient
    private List<Episodio> episodios;
    
    public Serie(){

    }
    public Serie(DatosSerie datosSerie) {
        this.titulo = datosSerie.titulo();
        this.totaltemporadas = datosSerie.totaltemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
        this.caratula = datosSerie.caratula();
        this.genero = Genero.fromString(datosSerie.genero().split(",")[0].trim());
        this.actores = datosSerie.actores();
        // this.sinopsis = ConsultaChatGpt.obtenerTraduccion(datosSerie.sinopsis());
        this.sinopsis = datosSerie.sinopsis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotaltemporadas() {
        return totaltemporadas;
    }

    public void setTotaltemporadas(Integer totaltemporadas) {
        this.totaltemporadas = totaltemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    @Override
    public String toString() {
        return "Serie [titulo= " + titulo +
                ", genero= " + genero +
                ", totaltemporadas= " + totaltemporadas +
                ", evaluacion= " + evaluacion +
                ", sinopsis= " + sinopsis +
                ", caratula= " + caratula +
                ", actores= " + actores
                + "]";
    }

}
