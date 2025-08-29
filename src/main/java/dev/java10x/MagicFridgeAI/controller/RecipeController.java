package dev.java10x.MagicFridgeAI.controller;

import dev.java10x.MagicFridgeAI.model.FooditemDTO;
import dev.java10x.MagicFridgeAI.service.ChatGptService;
import dev.java10x.MagicFridgeAI.service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {

    private FoodItemService foodItemService;
    private ChatGptService chatGptService;

    public RecipeController(ChatGptService chatGptService, FoodItemService foodItemService) {
        this.chatGptService = chatGptService;
        this.foodItemService = foodItemService;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe(){
        List<FooditemDTO> fooditems = foodItemService.listar();
        return chatGptService.generateRecipe(fooditems)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());


    }
}
