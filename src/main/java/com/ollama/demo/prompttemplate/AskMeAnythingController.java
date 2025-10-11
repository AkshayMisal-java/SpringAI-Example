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
public class AskMeAnythingController {

	@Autowired
    private OllamaAiService service;

    @GetMapping("/showAskAnything")
    public String showAskAnything() {
         return "askAnything";
    }

    @PostMapping("/askAnything")
    @TrackExecutionTime
    public String askAnything(@RequestParam("question") String question, Model model) {
    	
    	String response = service.generateAnswer(question);
    	model.addAttribute("question",question);
    	model.addAttribute("answer",response);
        return "askAnything";
    }
}