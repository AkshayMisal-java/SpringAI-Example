package com.ollama.demo.prompttemplate;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.ollama.demo.configuration.TrackExecutionTime;
import com.ollama.demo.services.OllamaAiService;

@Controller
public class JobSearchHelper {

	@Autowired
	private OllamaAiService service;

	@GetMapping("/showJobSearchHelper")
	public String showJobSearchHelper() {
		return "jobSearchHelper";

	}

	@PostMapping("/jobSearchHelper")
	@TrackExecutionTime
	public String jobSearchHelper(@RequestParam String query, Model model) {
		List<Document> response = service.searchJobs(query);
		model.addAttribute("response", response);
		return "jobSearchHelper";

	}

}