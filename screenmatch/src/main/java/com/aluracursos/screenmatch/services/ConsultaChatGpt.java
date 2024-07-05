package com.aluracursos.screenmatch.services;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGpt {
    public static String obtenerTraduccion(String texto) {
        
        OpenAiService service = new OpenAiService("Api-Key");

        try {
            CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduce a español el siguiente texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var respuesta = service.createCompletion(requisicion);
        return respuesta.getChoices().get(0).getText();
        } catch (Exception e) {
            return e.getMessage().split(":")[0];
        }
        
    }


}
