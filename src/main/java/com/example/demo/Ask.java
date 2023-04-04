package com.example.demo;

import com.example.openai.completion.CompletionChoice;
import com.example.openai.completion.CompletionRequest;
import com.example.openai.completion.chat.ChatCompletionRequest;
import com.example.service.OpenAiService;

public class Ask {
    public static String askQuestion(String token, String question, Double temperature) {
        String answer;

        OpenAiService service = new OpenAiService(token);

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(question)
                .model("text-davinci-003")
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
