package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
