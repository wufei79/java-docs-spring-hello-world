package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.HistoryDTO;
import com.example.model.History;
import com.example.model.HistoryExample;
import com.example.service.HistoryService;
import com.example.util.ResultModel;

@RestController
public class HistoryController {
    @Autowired
	public HistoryService historyService;

    @RequestMapping("/readAnswer/{conversationId}")
	public ResultModel readAnswer(@PathVariable("conversationId") String conversationId) throws Exception{
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

    @RequestMapping("/getConversationList")
    public ResultModel getConversationList() {
        List<Map<Integer, String>> conversationIds = new ArrayList<>();
        try {
            conversationIds = historyService.getConversationList();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return ResultModel.success("success", "getConversationList", conversationIds);
    }

    @RequestMapping("/getHistories/{conversationId}")
    public ResultModel getHistories(@PathVariable("conversationId") String conversationId) throws Exception{
        HistoryExample example = new HistoryExample();
        example.createCriteria()
        .andConversationIdEqualTo(conversationId)
        .andLatestEqualTo(1);
        List<History> histories = historyService.selectByExample(example);
        
		return ResultModel.success("success", "getHistories", histories);
    }

    @RequestMapping(value="/updateHistory", method=RequestMethod.POST)
    @ResponseBody
    public ResultModel getHistories(@RequestBody HistoryDTO historyDTO) throws Exception{
        History history = History.builder()
        .id(historyDTO.getId())
        .conversationId(historyDTO.getConversationId())
        .latest(historyDTO.getLatest())
        .build();
        //List<History> histories = historyService.selectByExample(example);
        int result = historyService.update(history);
        
		return ResultModel.success("success", "updateHistory", result);
    }
}
