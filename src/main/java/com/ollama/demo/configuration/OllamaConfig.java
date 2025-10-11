package com.ollama.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {

//    @Bean
//    public OllamaClient ollamaClient() {
//        return new OllamaClient("http://localhost:11434"); // उदाहरण
//    }
//
//    // Chat uses gemma3:4b via Spring AI auto-config (no change needed if properties set)
//    // If you need an embedding helper:
//    @Bean
//    public EmbeddingService embeddingService(OllamaClient client) {
//        return new OllamaEmbeddingService(client, "nomic-embed-text");
//    }
}