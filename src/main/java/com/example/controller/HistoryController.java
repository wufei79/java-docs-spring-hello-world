package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.History;
import com.example.model.HistoryExample;
import com.example.service.HistoryService;
import com.example.util.ResultModel;

@CrossOrigin
@RestController
public class HistoryController {
    @Autowired
	public HistoryService historyService;

    @RequestMapping("/readAnswer/{conversationId}")
	public ResultModel readAnswer(@PathVariable("conversationId") String conversationId) {
		String question = "",answer = "";
        HistoryExample example = new HistoryExample();
        example.createCriteria()
        .andConversationIdEqualTo(conversationId)
        .andLatestEqualTo(1);
        List<History> histories = historyService.selectByExample(example);
        if (null != histories) {
            if (histories.size() > 0) {
                question = histories.get(histories.size()-1).getQuestion();
                answer = histories.get(histories.size()-1).getAnswer();
            }
        }
        
		return ResultModel.success("success", question, answer);
	}

}
