package com.ollama.demo.prompttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ollama.demo.configuration.TrackExecutionTime;
import com.ollama.demo.services.OllamaAiService;

@Controller
public class InterviewHelperController {
	@Autowired
	private OllamaAiService service;

	@GetMapping("/showInterviewHelper")
	public String showChatPage() {
		return "interviewHelper";
	}

	@PostMapping("/interviewHelper")
	@TrackExecutionTime
	public String getChatResponse(
			@RequestParam("company") String company, 
			@RequestParam("jobTitle") String jobTitle,
			@RequestParam("strength") String strength, 
			@RequestParam("weakness") String weakness, Model model) {
		
		String response = service.getInterviewHelp(company, jobTitle, strength, weakness);
		model.addAttribute("response", response);
		return "interviewHelper";
	}

}
