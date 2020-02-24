package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Getter
@Setter
@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        Recipe recipe = recipeOptional.get(); // note use get to get the value of optional

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                // note that at the end we obtain Optional
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        // add not found exception here


        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        return ingredientCommand;
    }


    @Override
    @Transactional
    public IngredientCommand saveIngredient(IngredientCommand command) {
        // note the command has recipeid and ingredientid
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        Recipe recipe = recipeOptional.get(); // note use get to get the value of optional

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

        Ingredient ingredientFound = ingredientOptional.get();


        ingredientFound.setAmount(command.getAmount());
        ingredientFound.setDescription(command.getDescription());

        // note here add if the ingredientOptional exists, otherwise we add new ingredient
        // note for uom we need to iterate to find the corresponding uom based on uomid that command's uom held
        if(ingredientOptional.isPresent()){
            ingredientFound.setUom(unitOfMeasureRepository
                    .findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
        }else{
            recipe.addIngredients(ingredientCommandToIngredient.convert(command));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                .findFirst()
                .get());
    }

}
