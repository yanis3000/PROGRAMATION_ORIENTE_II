package com.example.examenminecraft;

public class MinecraftException extends RuntimeException {

    private String type;
    private String couleur;
    public MinecraftException(String message) {
        super(message /* */ );
    }

}
