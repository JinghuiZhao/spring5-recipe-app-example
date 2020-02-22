package guru.springframework.controllers;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class RecipeController {

    // now we use the service instead of the repository
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping
    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    // handle post request,@ModelAttribute tell spring to bind form post parameters to recipecommand object
    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        // service returns back a new implementation of the command
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        log.debug("this recipe is ok");
        return "redirect:/recipe/show/" + savedCommand.getId();
    }


    @GetMapping
    @RequestMapping("recipe/{id}/update")
    // we return the page same as creating newRecipe page
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommand(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @GetMapping // we only expect get method here
    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        log.debug("deleting" + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

}
