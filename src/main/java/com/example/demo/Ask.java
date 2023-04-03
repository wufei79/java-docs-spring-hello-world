package com.example.demo;

import com.example.openai.completion.CompletionChoice;
import com.example.openai.completion.CompletionRequest;
import com.example.service.OpenAiService;

public class Ask {
    public static String askQuestion(String question, Double temperature) {
        String answer;


        String token = "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";
        OpenAiService service = new OpenAiService(token);

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
            java.util.List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
            answer = choices.get(0).getText();
            System.out.println("Answer has " + choices.size() + " choices");
        } catch(Exception ex) {
            ex.printStackTrace();
            answer = ex.getMessage();
        }

        return answer;

    }
}
