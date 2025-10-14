package com.ollama.demo;

import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

	@Autowired
	private VectorStore vectorStore;
	
	@PostConstruct
	public void init() {
		
//		TextReader jobListingReader = new TextReader(new ClassPathResource("job_listings.txt"));
		TokenTextSplitter textSplitter = new TokenTextSplitter(100, 100, 5, 1000, true);
//		List<Document> documents = textSplitter.split(jobListingReader.get());
//		vectorStore.add(documents);
//		
//		TextReader productDataReader = new TextReader(new ClassPathResource("product-data.txt"));
//		vectorStore.add(textSplitter.split(productDataReader.get()));
				
		TextReader legaltDataReader = new TextReader(new ClassPathResource("Legal_Document_Analysis_Data.txt"));
		vectorStore.add(textSplitter.split(legaltDataReader.get()));
		
//		TextReader supportTicketsDataReader = new TextReader(new ClassPathResource("support_tickets.txt"));
//		vectorStore.add(textSplitter.split(supportTicketsDataReader.get()));
		
	}
}
