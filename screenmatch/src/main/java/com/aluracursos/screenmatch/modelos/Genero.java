package com.aluracursos.screenmatch.modelos;

public enum Genero {
    Accion("Action"),
    Romance("Romance"),
    Comedia("Comedy"),
    Drama("Drama"),
    Crimen("Crime");


    private String categoriaOmdb;

    Genero( String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Genero fromString(String text) {
        for (Genero categoria : Genero.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

}
