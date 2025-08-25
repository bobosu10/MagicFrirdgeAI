package dev.java10x.MagicFridgeAI.service;

import dev.java10x.MagicFridgeAI.config.WebClientConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private String apiKey = System.getenv("API_KEY");

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(){
        String prompt = "Me sugira uma receita simples com ingredientes comuns";

    }
}
