package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.History;
import com.example.openai.completion.CompletionChoice;
import com.example.openai.completion.CompletionRequest;
import com.example.openai.completion.chat.ChatCompletionChoice;
import com.example.openai.completion.chat.ChatCompletionRequest;
import com.example.openai.completion.chat.ChatMessage;
import com.example.openai.completion.chat.ChatMessageRole;
import com.example.service.AskService;
import com.example.service.OpenAiService;
import com.example.service.HistoryService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class AskServiceImpl implements AskService {
    @Autowired
    public HistoryService historyService;

    private List<ChatMessage> conversation = new LinkedList<ChatMessage>();
    private String conversationID = "";

    @Override
    public void clearConversation(String conversationId) {
        conversation.clear();
        this.conversationID = conversationId;
    }

    @Override
    public String testSave() {
        String answer = "";
        History history = History.builder()
                        .conversationId("conversationId")
                        .model("model")
                        .temperature(0.0)
                        .maxTokens(0)
                        .question("question")
                        .answer("answer")
                        .finishReason("finish_reason")
                        .latest(1)
                        .dateTime(new java.util.Date())
                        .build();

        int result = historyService.insert(history);

        if (1==result) {
            answer = "success";
        } else {
            answer = "fail";
        }

        return answer;
    }

    @Override
    public String askCompletionQuestion(String token, String question, Double temperature, int maxTokens) {
        String answer = "";

        OpenAiService service = new OpenAiService(token);

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(question)
                .model("text-davinci-003")
                .echo(true)
                .temperature(temperature)
                .topP(1.0)
                .stop(Arrays.asList("\n","\n"))
                .maxTokens(maxTokens)
                .build();
        
        //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        try {
            java.util.List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
            if (null != choices) {
                if (null != choices.get(0)) {
                    answer = choices.get(0).getText();
                    //System.out.println("Answer has " + choices.size() + " choices");

                    History history = History.builder()
                        .conversationId("completion")
                        .model(completionRequest.getModel())
                        .temperature(completionRequest.getTemperature())
                        .maxTokens(completionRequest.getMaxTokens())
                        .question(question)
                        .answer(answer)
                        .finishReason(choices.get(0).getFinish_reason())
                        .latest(1)
                        .dateTime(new java.util.Date())
                        .build();

                    int result = historyService.insert(history);

                    if (1==result) {
                        answer = "success";
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            answer = ex.getMessage();
        }

        return answer;
    }

    @Override
    public String askChatQuestion(String token, String question, Double temperature, int maxTokens) {
        String answer = "";

        OpenAiService service = new OpenAiService(token);

        //List<ChatMessage> messages = new LinkedList<ChatMessage>();
        //messages.add(conversation.forEach(null));
        conversation.add(new ChatMessage(ChatMessageRole.USER.value(), question));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(conversation)
                .temperature(temperature)
                .topP(1.0)
                .stop(null)
                .maxTokens(maxTokens)
                .build();
        
        //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        try {
            java.util.List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();
            if (null != choices.get(0)) {
                conversation.remove(conversation.size()-1);
                ChatCompletionChoice choice = choices.get(0);
                answer = choice.getMessage().getContent();
                conversation.add(new ChatMessage(ChatMessageRole.USER.value(), question));
                conversation.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(), answer));

                History history = History.builder()
                .conversationId(conversationID)
                .model(chatCompletionRequest.getModel())
                .temperature(chatCompletionRequest.getTemperature())
                .maxTokens(chatCompletionRequest.getMaxTokens())
                .question(question)
                .answer(answer)
                .finishReason(choice.getFinishReason())
                .latest(1)
                .dateTime(new java.util.Date())
                .build();

                int result = historyService.insert(history);

                if (1==result) {
                    answer = "success";
                }
            }
            System.out.println("Answer has " + choices.size() + " choices");
        } catch(Exception ex) {
            conversation.remove(conversation.size()-1);

            ex.printStackTrace();
            answer = ex.getMessage();
        }

        return answer;

    }
}
