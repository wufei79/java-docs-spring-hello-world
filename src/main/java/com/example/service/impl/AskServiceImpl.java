package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import com.example.dto.QuestionDTO;
import com.example.model.History;
import com.example.model.HistoryExample;
import com.example.openai.completion.CompletionChoice;
import com.example.openai.completion.CompletionRequest;
import com.example.openai.completion.CompletionResult;
import com.example.openai.completion.chat.ChatCompletionChoice;
import com.example.openai.completion.chat.ChatCompletionRequest;
import com.example.openai.completion.chat.ChatCompletionResult;
import com.example.openai.completion.chat.ChatMessage;
import com.example.openai.completion.chat.ChatMessageRole;
import com.example.service.AskService;
import com.example.service.OpenAiService;
import com.example.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class AskServiceImpl implements AskService {
    @Autowired
    public HistoryService historyService;

    OpenAiService service;
    static int maxConversationLength = 3;

    @Override
    public String setKey(String key) {
        service = new OpenAiService(key);
        return "success";
    }

    //private List<ChatMessage> conversation = new LinkedList<ChatMessage>();
    //private String currentConversationID = "";
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void clearConversation(String conversationId) {
        //conversation.clear();
        //this.currentConversationID = conversationId;
    }

    @Override
    public String testSave() throws Exception{
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
                        .response("response")
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
    public String askCompletionQuestion(QuestionDTO questionDTO) {
        String answer = "";

        //OpenAiService service = new OpenAiService(token);

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(questionDTO.getQuestion())
                .model("text-davinci-003")
                .echo(true)
                .temperature(questionDTO.getTemperature())
                .topP(1.0)
                .stop(Arrays.asList("\n","\n"))
                .maxTokens(questionDTO.getMaxTokens())
                .build();
        
        //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        try {
            CompletionResult completionResult = service.createCompletion(completionRequest);
            java.util.List<CompletionChoice> choices = completionResult.getChoices();
            if (null != choices) {
                if (null != choices.get(0)) {
                    answer = choices.get(0).getText();
                    //System.out.println("Answer has " + choices.size() + " choices");

                    History history = History.builder()
                        .conversationId("completion")
                        .model(completionRequest.getModel())
                        .temperature(completionRequest.getTemperature())
                        .maxTokens(completionRequest.getMaxTokens())
                        .question(questionDTO.getQuestion())
                        .answer(answer)
                        .finishReason(choices.get(0).getFinish_reason())
                        .latest(1)
                        .dateTime(new java.util.Date())
                        .response(mapper.writeValueAsString(completionResult))
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
    public String askChatQuestion(QuestionDTO questionDTO) throws Exception{
        String answer = "";

        //List<ChatMessage> messages = new LinkedList<ChatMessage>();
        //messages.add(conversation.forEach(null));
        //currentConversationID = StringUtils.isNotBlank(questionDTO.getConversationId())?questionDTO.getConversationId():currentConversationID;
        List<ChatMessage> conversations = loadHistoryConversations(questionDTO.getConversationId(), questionDTO.getConversationLength());
        conversations.add(new ChatMessage(ChatMessageRole.USER.value(), questionDTO.getQuestion()));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(conversations)
                .temperature(questionDTO.getTemperature())
                .topP(1.0)
                .stop(null)
                .maxTokens(questionDTO.getMaxTokens())
                .build();
        
        //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
            ChatCompletionResult chatCompletionResult = service.createChatCompletion(chatCompletionRequest);
            java.util.List<ChatCompletionChoice> choices = chatCompletionResult.getChoices();
            if (null != choices) {
                if (null != choices.get(0)) {
                    ChatCompletionChoice choice = choices.get(0);
                    answer = choice.getMessage().getContent();
                    //conversation.remove(conversation.size()-1);
                    //conversation.add(new ChatMessage(ChatMessageRole.USER.value(), questionDTO.getQuestion()));
                    //conversation.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(), answer));

                    History history = History.builder()
                    .conversationId(questionDTO.getConversationId())
                    .model(chatCompletionRequest.getModel())
                    .temperature(chatCompletionRequest.getTemperature())
                    .maxTokens(chatCompletionRequest.getMaxTokens())
                    .question(questionDTO.getQuestion())
                    .answer(answer)
                    .finishReason(choice.getFinishReason())
                    .latest(1)
                    .dateTime(new java.util.Date())
                    .response(mapper.writeValueAsString(chatCompletionResult))
                    .build();

                    int result = historyService.insert(history);

                    if (1==result) {
                        answer = "success";
                    }
                }
            }
            //System.out.println("Answer has " + choices.size() + " choices");

        return answer;

    }

    private LinkedList<ChatMessage> loadHistoryConversations(String conversationId, int conversationLength) throws Exception {
        LinkedList<ChatMessage> conversations = new LinkedList<ChatMessage>();
        HistoryExample example = new HistoryExample();
        example.createCriteria().andConversationIdEqualTo(conversationId);
        example.setOrderByClause("id asc");
        
            List<History> histories = historyService.selectByExample(example);
            if (null != histories) {
                if (histories.size()>0) {
                    int start = 0;
                    int end = 0;

                    if (histories.size()<conversationLength) {
                        start = 0;
                        end = histories.size();
                    } else {
                        if (0 == conversationLength) {
                            start = 0;
                            end = histories.size()<maxConversationLength?histories.size():maxConversationLength;
                        } else {
                            start = histories.size() - conversationLength;
                            end =  histories.size();
                        }
                    }
                    
                    for (int i=start; i<end; i++) {
                        ChatMessage question = new ChatMessage();
                        question.setRole(ChatMessageRole.USER.value());
                        question.setContent(histories.get(i).getQuestion());
                        conversations.add(question);
                        
                        ChatMessage answer = new ChatMessage();
                        answer.setRole(ChatMessageRole.ASSISTANT.value());
                        answer.setContent(histories.get(i).getAnswer());
                        conversations.add(answer);
                    }
                }
            }
        return conversations;
    }
}
