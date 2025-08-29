package dev.java10x.MagicFridgeAI.service;

import dev.java10x.MagicFridgeAI.model.FooditemDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private String apiKey;

    public ChatGptService(WebClient webClient,@Value("${api.key}") String apiKey) {
        this.webClient = webClient;
        this.apiKey = apiKey;
    }

    public Mono<String> generateRecipe(List<FooditemDTO> fooditems) {

        String alimentos = fooditems.stream()
                .map(item -> String.format(
                        "%s (Categoria: %s) - Quantidade: %d, Validade: %s",
                        item.getNome(),
                        item.getCategoria(),
                        item.getQuantidade(),
                        item.getValidade().toString()
                ))
                .collect(Collectors.joining("\n"));

        String prompt = "Baseado no meu banco de dados faca uma receita com os seguintes itens:\n " +alimentos;

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-5-nano",
                "input", prompt
        );

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    try {
                        var output = (List<Map<String, Object>>) response.get("output");
                        if (output != null && !output.isEmpty()) {
                            for (Map<String, Object> item : output) {
                                if ("message".equals(item.get("type"))) {
                                    var content = (List<Map<String, Object>>) item.get("content");
                                    if (content != null && !content.isEmpty()) {
                                        return content.get(0).get("text").toString();
                                    }
                                }
                            }
                        }
                        return "Nenhuma receita foi gerada.";
                    } catch (Exception e) {
                        return "Erro ao interpretar a resposta da API: " + e.getMessage();
                    }
                })
                .onErrorReturn("Erro de requisicao para API do ChatGPT.");
    }
}

