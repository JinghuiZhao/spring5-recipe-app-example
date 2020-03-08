package guru.springframework.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;

    @NotNull(message="ingredient description cannot be empty")
    private String description;

    @NotNull(message="ingredient amount cannot be empty")
    private BigDecimal amount;
    // note set uom here since we set the variable name as uom initially in ingredient entity
    private UnitOfMeasureCommand uom;
    private Long recipeId;
    private RecipeCommand recipeCommand;
}
