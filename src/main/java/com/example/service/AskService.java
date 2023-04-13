package com.example.service;

import com.example.dto.QuestionDTO;

public interface AskService {
    public String testSave() throws Exception;

    public String setKey(String key);
    
    public void clearConversation(String conversationId);

    public String askCompletionQuestion(QuestionDTO questionDTO);

    public String askChatQuestion(QuestionDTO questionDTO);

}
