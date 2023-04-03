package com.example.demo;

import com.example.openai.completion.CompletionRequest;
import com.example.service.OpenAiService;

public class Ask {
    public static String askQuestion(String question, Double temperature) {
        String answer;


        String token = "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";
        OpenAiService service = new OpenAiService(token);

        //System.out.println("\nCreating completion...");
        /*
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt("Somebody once told me the world is gonna roll me")
                .echo(true)
                .user("testing")
                .n(3)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        */

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(question)
                .model("gpt-3.5-turbo")
                .echo(true)
                .temperature(temperature)
                .topP(1.0)
                .stop(null)
                .maxTokens(200)
                .build();
        
        //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        try {
            answer = service.createCompletion(completionRequest).getChoices().get(0).getText();
        } catch(Exception ex) {
            answer = ex.getMessage();
        }

        return answer;

        /*
        System.out.println("\nCreating Image...");
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt("A cow breakdancing with a turtle")
                .build();

        System.out.println("\nImage is located at:");
        System.out.println(service.createImage(request).getData().get(0).getUrl());
        */

        //return "success";
    }
}
