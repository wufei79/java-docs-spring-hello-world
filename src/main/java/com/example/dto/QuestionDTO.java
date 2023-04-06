package com.example.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    //private String key;
    private String question;
    private double temperature;
    private int maxTokens;
    private String conversationId;
    private int conversationLength;
}