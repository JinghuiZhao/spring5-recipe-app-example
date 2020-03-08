package guru.springframework.commands;

import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    @NotNull(message="recipe description cannot be empty")
    private String description;

    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();
    private Byte[] image;

}
