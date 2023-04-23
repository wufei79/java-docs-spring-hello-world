package com.example.dto;

import lombok.Data;

@Data
public class HistoryDTO {
    int id;
    String conversationId;
    int latest;
}
