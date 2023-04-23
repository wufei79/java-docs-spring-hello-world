package com.example.service;

import com.example.model.History;
import com.example.model.HistoryExample;

import java.util.List;
import java.util.Map;

public interface HistoryService {
    int insert(History history) throws Exception;
    List<History> selectByExample(HistoryExample example) throws Exception;
    List<String> getConversationList() throws Exception;
    List<History> getHistories(String conversationId) throws Exception;
    int update(History history) throws Exception;
}
