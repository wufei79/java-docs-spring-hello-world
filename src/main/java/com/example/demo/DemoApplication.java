package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.*;
import com.example.util.*;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	String sayHello() {
		return "Hello 123!";
	}

	@RequestMapping("/test")
	String test() {
		return "it's a test";
	}

	@RequestMapping(value="/question",method = RequestMethod.POST)
    @ResponseBody
    public ResultModel getAnswer(@RequestBody QuestionDTO questionDTO){
		if (null==questionDTO) {
			return ResultModel.error("questionDTO is null");
		}
		if (null==questionDTO.getQuestion()) {
			return ResultModel.error("question is null");
		}
		return ResultModel.success(questionDTO.getQuestion());
	}
}
