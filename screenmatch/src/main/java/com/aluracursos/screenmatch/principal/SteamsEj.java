package com.aluracursos.screenmatch.principal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class SteamsEj {

    public static void main(String[] args) {
    
    monstrarEjemplo();



    }

    public static void monstrarEjemplo(){
        List<String> nombres = Arrays.asList("Maria","Pedro","Pedro","Angel","Berto","Alejandro");

        nombres.stream()
            .sorted()
            .filter((e)->e.startsWith("A"))
            .map((e)->e.toUpperCase())
            .limit(3)
            .forEach(System.out::println);
    }
}
