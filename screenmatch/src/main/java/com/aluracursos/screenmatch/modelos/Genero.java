package com.aluracursos.screenmatch.modelos;

public enum Genero {
    Accion("Action","Acción"),
    Romance("Romance","Romance"),
    Comedia("Comedy","Comedia"),
    Drama("Drama","Dráma"),
    Crimen("Crimen","Crimen");


    private String categoriaOmdb;
    private String categoriaEspanol;

    Genero( String categoriaOmdb, String categoriaEspanol){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static Genero fromString(String text) {
        for (Genero categoria : Genero.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    public static Genero fromEspanol(String text) {
        for (Genero categoria : Genero.values()) {
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

}
