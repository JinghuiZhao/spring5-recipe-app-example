package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf.
        //model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
        model.addAttribute("recipe", recipeService.findRecipeCommand(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                              @PathVariable String id,
                              Model model){
        log.debug("Showing ingredient list for recipe id: " + recipeId);
        model.addAttribute("ingredient", ingredientService.findRecipeIdAndIngredientId(Long.valueOf(recipeId),
                Long.valueOf(id)));

        return "recipe/ingredient/show";  //ingredient/show is for a single ingredient
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                              @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findRecipeIdAndIngredientId(Long.valueOf(recipeId),
                Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";  //ingredient/show is for a single ingredient
    }


    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredient(command);
        log.debug("saved ingredient id:" + savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";

    }

}
