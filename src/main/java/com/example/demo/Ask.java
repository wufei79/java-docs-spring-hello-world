package com.example.demo;

import com.example.openai.completion.CompletionChoice;
import com.example.openai.completion.CompletionRequest;
import com.example.openai.completion.chat.ChatCompletionChoice;
import com.example.openai.completion.chat.ChatCompletionRequest;
import com.example.openai.completion.chat.ChatMessage;
import com.example.openai.completion.chat.ChatMessageRole;
import com.example.service.OpenAiService;

import java.util.LinkedList;
import java.util.List;

public class Ask {
    public static String askCompletionQuestion(String token, String question, Double temperature, int maxTokens) {
        String answer;

        OpenAiService service = new OpenAiService(token);

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(question)
                .model("text-davinci-003")
                .echo(true)
                .temperature(temperature)
                .topP(1.0)
                .stop(null)
                .maxTokens(maxTokens)
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

    public static String askChatQuestion(String token, String question, Double temperature, int maxTokens) {
        String answer;

        OpenAiService service = new OpenAiService(token);

        List<ChatMessage> messages = new LinkedList<ChatMessage>();
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), question));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .temperature(temperature)
                .topP(1.0)
                .stop(null)
                .maxTokens(maxTokens)
                .build();
        
        //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        try {
            java.util.List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();
            answer = choices.get(0).getMessage().getContent();
            System.out.println("Answer has " + choices.size() + " choices");
        } catch(Exception ex) {
            ex.printStackTrace();
            answer = ex.getMessage();
        }

        return answer;

    }
}
