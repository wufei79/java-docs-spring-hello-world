package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.*;
import com.example.service.AskService;
import com.example.util.*;

@CrossOrigin
@RestController
public class AskController {
    @Autowired
	public AskService ask;

	@RequestMapping("/")
	String sayHello() {
		return "Hello 123!";
	}

	@RequestMapping("/testSave")
	public ResultModel testSave() {
		return ResultModel.success("success", "testSave", ask.testSave());
	}

	@RequestMapping("/setKey/{key}")
	public ResultModel setKey(@PathVariable("key") String key) {
		ask.setKey(key);
		return ResultModel.success("success", "setKey", "success");
	}

	@RequestMapping("/clearConversation/{conversationId}")
	public ResultModel clearConversation(@PathVariable("conversationId") String conversationId){
		ask.clearConversation(conversationId);
		return ResultModel.success("success", "clearConversion", ""); 
	}

	@RequestMapping(value="completion/question",method = RequestMethod.POST)
    @ResponseBody
    public ResultModel getCompletionAnswer(@RequestBody QuestionDTO questionDTO){
		if (null==questionDTO) {
			return ResultModel.error("questionDTO is null");
		}
		if (null==questionDTO.getQuestion()) {
			return ResultModel.error("question is null");
		}

		String question = questionDTO.getQuestion();
		String answer = ask.askCompletionQuestion(questionDTO);
		return ResultModel.success("success", question, answer);
	}

	@RequestMapping(value="chat/question",method = RequestMethod.POST)
	@ResponseBody
    public ResultModel getChatAnswer(@RequestBody QuestionDTO questionDTO){
		if (null==questionDTO) {
			return ResultModel.error("questionDTO is null");
		}
		if (null==questionDTO.getQuestion()) {
			return ResultModel.error("question is null");
		}

		String question = questionDTO.getQuestion();
		String answer = ask.askChatQuestion(questionDTO);
		return ResultModel.success("success", question, answer);
	}
}
