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
public class embeddingController {
	@Autowired
	private OllamaAiService service;

	@GetMapping("/showEmbedding")
	public String showChatPage() {
		return "embedDemo";
	}

	@PostMapping("/embedding")
	@TrackExecutionTime
	public String getChatResponse(
			@RequestParam("text") String text, Model model) {
		
		float[] response = service.getEmbeding(text);
		model.addAttribute("response", response);
		return "embedDemo";
	}
	
	@GetMapping("/showSimilarity")
	public String showSimilirityPage() {
		return "similarityFinder";
	}

	@PostMapping("/similarityFinder")
	@TrackExecutionTime
	public String findSimalirity(
			@RequestParam("text1") String text1, @RequestParam("text2") String text2, Model model) {
		
		double response = service.similarityFinder(text1, text2);
		model.addAttribute("response", response);
		model.addAttribute("text1", text1);
		model.addAttribute("text2", text2);
		return "similarityFinder";
	}

}
