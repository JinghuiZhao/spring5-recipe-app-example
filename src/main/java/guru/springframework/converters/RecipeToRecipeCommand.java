package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;


    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;}

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source != null) {
            final RecipeCommand command = new RecipeCommand();
            command.setId(source.getId());
            command.setDescription(source.getDescription());
            command.setPrepTime(source.getPrepTime());
            command.setServings(source.getServings());
            command.setCookTime(source.getCookTime());
            command.setSource(source.getSource());
            command.setUrl(source.getUrl());
            command.setImage(source.getImage());
            command.setDirections(source.getDirections());
            command.setNotes(notesConverter.convert(source.getNotes()));
            command.setDifficulty(source.getDifficulty());


            // we need to convert each of ingredients to commandtype using the converter
            if (source.getIngredients()!=null && source.getIngredients().size() > 0){
                source.getIngredients()
                        .forEach((ingredient) -> command.getIngredients()
                                .add(ingredientConverter.convert(ingredient)));
            }


            if (source.getCategories()!= null && source.getCategories().size() > 0){
                source.getCategories()
                        .forEach((category) -> command.getCategories()
                        .add(categoryConverter.convert(category)));
            }

            return command;
        }
        return null;
    }
}
