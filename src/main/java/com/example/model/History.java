package com.example.model;

import java.util.Date;

import lombok.Builder;

@Builder
public class History {
    private Integer id;

    private String conversationId;

    private String model;

    private Double temperature;

    private Integer maxTokens;

    private String question;

    private String answer;

    private String finishReason;

    private Date dateTime;

    private Integer latest;

    private String response;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getLatest() {
        return latest;
    }

    public void setLatest(Integer latest) {
        this.latest = latest;
    }

    public String geResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}