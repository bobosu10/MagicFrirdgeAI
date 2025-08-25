package dev.java10x.MagicFridgeAI.service;

import dev.java10x.MagicFridgeAI.model.FoodItemMapper;
import dev.java10x.MagicFridgeAI.model.FoodItemModel;
import dev.java10x.MagicFridgeAI.model.FooditemDTO;
import dev.java10x.MagicFridgeAI.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodItemService {

    private FoodItemRepository repository;
    private FoodItemMapper foodItemMapper;

    public FoodItemService(FoodItemMapper foodItemMapper, FoodItemRepository repository) {
        this.foodItemMapper = foodItemMapper;
        this.repository = repository;
    }

    //criar
    public FooditemDTO salvar(FooditemDTO fooditemDTO){
        FoodItemModel food = foodItemMapper.map(fooditemDTO);
        food = repository.save(food);
        return foodItemMapper.map(food);
    }

    //listar
    public List<FooditemDTO> listar(){
        List<FoodItemModel> foods = repository.findAll();
        return foods.stream()
                .map(foodItemMapper::map)
                .collect(Collectors.toList());
    }

    //buscar por id
    public FooditemDTO listarPorId(Long id){
        Optional<FoodItemModel> food = repository.findById(id);
        return food.map(foodItemMapper::map).orElse(null);
    }

    //alterar
    public FooditemDTO alterar(Long id,FooditemDTO fooditemDTO){
        Optional<FoodItemModel> foodExistente = repository.findById(id);
        if (foodExistente.isPresent()){
            FoodItemModel foodUpdate = foodItemMapper.map(fooditemDTO);
            foodUpdate.setId(id);
            FoodItemModel foodSave = repository.save(foodUpdate);
                return foodItemMapper.map(foodSave);
        }else {
        return null;
        }
    }

    //deletar
    public String deletar(Long id){
        Optional<FoodItemModel> food = repository.findById(id);
        if (food.isPresent()){
            repository.deleteById(id);
            return "Item deletado.";
        }else {
            return "Item nao encontrado.";
        }
    }


    //fazer listar por id alterar deletar
}
