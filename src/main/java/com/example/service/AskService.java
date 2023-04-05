package com.example.service;

public interface AskService {
    public void clearConversation();

    public String askCompletionQuestion(String token, String question, Double temperature, int maxTokens);

    public String askChatQuestion(String token, String question, Double temperature, int maxTokens);

    public String test();

}
