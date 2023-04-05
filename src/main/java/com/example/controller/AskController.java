package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.*;
import com.example.service.AskService;
import com.example.util.*;

@RestController
public class AskController {
    @Autowired
	public AskService ask;

	@RequestMapping("/")
	String sayHello() {
		return "Hello 123!";
	}

	@RequestMapping("/test")
	public ResultModel test() {
		String result = ask.test();
		return ResultModel.success("success", "test", result); 
	}

	@RequestMapping("/clearConversation")
	public ResultModel clearConversation(){
		ask.clearConversation();
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
		String answer = ask.askCompletionQuestion(questionDTO.getToken(), questionDTO.getQuestion(), new Double(0), questionDTO.getMaxTokens());
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
		String answer = ask.askChatQuestion(questionDTO.getToken(), questionDTO.getQuestion(), new Double(0), questionDTO.getMaxTokens());
		return ResultModel.success("success", question, answer);
	}
}
