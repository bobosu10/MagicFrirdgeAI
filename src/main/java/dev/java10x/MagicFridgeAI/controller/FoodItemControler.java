package dev.java10x.MagicFridgeAI.controller;


import dev.java10x.MagicFridgeAI.model.FoodItemModel;
import dev.java10x.MagicFridgeAI.model.FooditemDTO;
import dev.java10x.MagicFridgeAI.service.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodItemControler {

    private FoodItemService service;

    public FoodItemControler(FoodItemService service) {
        this.service = service;
    }

    //POST
    @PostMapping("/SalvarFood")
    public ResponseEntity<FooditemDTO> criar(@RequestBody FooditemDTO fooditemDTO){
        FooditemDTO salvo = service.salvar(fooditemDTO);
        return ResponseEntity.ok(salvo);
    }

    //GET
    @GetMapping("/listar/{id}")
    public ResponseEntity<FooditemDTO> get(@PathVariable Long id){
        FooditemDTO food = service.listarPorId(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(food);

    }

    //GET(listar)
    @GetMapping("/listar")
    public ResponseEntity<List<FooditemDTO>> listar(){
        List<FooditemDTO> foods = service.listar();
        return ResponseEntity.ok(foods);

    }

    //UPDATE
    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id,@RequestBody FooditemDTO fooditemDTO){
        FooditemDTO food = service.alterar(id,fooditemDTO);
        if (food != null){
            return ResponseEntity.ok(food);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item nao encontrado.");
        }

    }

    //DELETE
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        if (service.listarPorId(id) != null){
            service.deletar(id);
            return ResponseEntity.ok("Item deletado.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item nao encontrado.");
        }
    }
}
