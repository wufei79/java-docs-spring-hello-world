package com.example.service;

import com.example.model.History;
import com.example.model.HistoryExample;

import java.util.List;

public interface HistoryService {
    int insert(History history);
    List<History> selectByExample(HistoryExample example);
    List<String> getConversationList();
}
