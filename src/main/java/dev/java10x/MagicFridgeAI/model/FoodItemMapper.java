package dev.java10x.MagicFridgeAI.model;

import org.springframework.stereotype.Component;

@Component
public class FoodItemMapper {

    public FoodItemModel map(FooditemDTO fooditemDTO){

        FoodItemModel foodItemModel = new FoodItemModel();

        foodItemModel.setCategoria(fooditemDTO.getCategoria());
        foodItemModel.setId(fooditemDTO.getId());
        foodItemModel.setNome(fooditemDTO.getNome());
        foodItemModel.setValidade(fooditemDTO.getValidade());
        foodItemModel.setQuantidade(fooditemDTO.getQuantidade());

        return foodItemModel;
    }


    public FooditemDTO map(FoodItemModel foodItemModel){

        FooditemDTO fooditemDTO = new FooditemDTO();

        fooditemDTO.setCategoria(foodItemModel.getCategoria());
        fooditemDTO.setId(foodItemModel.getId());
        fooditemDTO.setNome(foodItemModel.getNome());
        fooditemDTO.setQuantidade(foodItemModel.getQuantidade());
        fooditemDTO.setValidade(foodItemModel.getValidade());

        return fooditemDTO;
    }
}
