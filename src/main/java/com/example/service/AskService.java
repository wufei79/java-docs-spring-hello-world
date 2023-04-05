package com.example.service;

public interface AskService {
    public String testSave();

    public void clearConversation(String conversationId);

    public String askCompletionQuestion(String token, String question, Double temperature, int maxTokens);

    public String askChatQuestion(String token, String question, Double temperature, int maxTokens);
}
