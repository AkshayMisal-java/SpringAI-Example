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
public class ProductDataBot {

	@Autowired
	private OllamaAiService service;

	@GetMapping("/showProductDataBot")
	public String showProductDataBot() {
		return "productDataBot";

	}

	@PostMapping("/productDataBot")
	@TrackExecutionTime
	public String productDataBot(@RequestParam String query, Model model) {
		model.addAttribute("response",service.answer(query));
		return "productDataBot";

	}

}