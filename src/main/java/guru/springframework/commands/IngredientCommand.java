package guru.springframework.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    // note set uom here since we set the variable name as uom initially in ingredient entity
    private UnitOfMeasureCommand uom;
    private Long recipeId; // since we need to find recipe to display ingredients

}
