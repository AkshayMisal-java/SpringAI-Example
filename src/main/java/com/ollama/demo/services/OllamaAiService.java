package com.ollama.demo.services;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ollama.demo.configuration.TrackExecutionTime;
import com.ollama.demo.dtos.CountryCuisines;

@Service
public class OllamaAiService {
	
	private ChatClient chatClient;
	
	@Autowired
	private EmbeddingModel embeddingModel;
	
	public OllamaAiService(ChatClient.Builder builder) {
		InMemoryChatMemory inMemoryChatMemory = new InMemoryChatMemory();
		MessageChatMemoryAdvisor messageChatMemoryAdvisor = new MessageChatMemoryAdvisor(inMemoryChatMemory);
		chatClient = builder.defaultAdvisors(messageChatMemoryAdvisor).build();
	}
	
	
	public String generateAnswer(String question) {
		return chatClient.prompt(question).call().chatResponse().getResult().getOutput().getContent();
	}

	
	public String getTravelGuidance(String city, String month, String language, String budget ) {
		PromptTemplate promptTemplate = new PromptTemplate("Welcome to the {city} travel guide!\n"
				+ "If you're visiting in {month}, here's what you can do:\n"
				+ "1. Must-visit attractions.\n"
				+ "2. Local cuisine you must try.\n"
				+ "3. Useful phrases in {language}.\n"
				+ "4. Tips for traveling on a {budget} budget.\n"
				+ "Enjoy your trip!\n"
				+ "");
		Prompt prompt = promptTemplate.create(
				Map.of("city", city, "month", month, "language", language, "budget", budget));
		return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getContent();
	}

	
	public CountryCuisines getCuisines(String country, String numCuisines, String language) {
		PromptTemplate promptTemplate = new PromptTemplate("You are an expert in traditional cuisines.\n"
				+ "You provide information about a specific dish from a specific\n"
				+ "country.\n"
				+ "Answer the question: What is the traditional cuisine of {country}?\n"
				+ "Return the list of {numCuisines} in {language}\n"
				+ "Avoid giving information about fictional places. If the country is\n"
				+ "fictional\n"
				+ "or non-existent answer: I don't know\n"
				+ "");
		Prompt prompt = promptTemplate.create(
				Map.of("country", country, "language", language, "numCuisines", numCuisines));
		return chatClient.prompt(prompt).call().entity(CountryCuisines.class);
	}

	
	public String getInterviewHelp(String company, String jobTitle, String strength, String weakness) {
		PromptTemplate promptTemplate = new PromptTemplate("You are a career coach. Provide tailored interview tips for the\n"
				+ "position of {position} at {company}.\n"
				+ "Highlight your strengths in {strengths} and prepare for questions\n"
				+ "about your weaknesses such as {weaknesses}");
		Prompt prompt = promptTemplate.create(
				Map.of("position", jobTitle, "company", company, "strengths", strength, "weaknesses", weakness));
		return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getContent();
	}

	
	public float[] getEmbeding(String text) {
		float[] embeddedings = embeddingModel.embed(text);
		return embeddedings;
	}
	
	
	public double similarityFinder(String text1, String text2) {
		List<float[]> response = embeddingModel.embed(List.of(text1, text2));
		return cosineSimilarity(response.get(0), response.get(1));
	}

	private double cosineSimilarity(float[] vectorA, float[] vectorB) {
		if (vectorA.length != vectorB.length) {
			throw new IllegalArgumentException("Vectors must be of the same length");
		}

		// Initialize variables for dot product and magnitudes
		double dotProduct = 0.0;
		double magnitudeA = 0.0;
		double magnitudeB = 0.0;

		// Calculate dot product and magnitudes
		for (int i = 0; i < vectorA.length; i++) {
			dotProduct += vectorA[i] * vectorB[i];
			magnitudeA += vectorA[i] * vectorA[i];
			magnitudeB += vectorB[i] * vectorB[i];
		}

		// Calculate and return cosine similarity
		return dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
	}

}
