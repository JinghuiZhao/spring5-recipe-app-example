package guru.springframework.controllers;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Controller
public class RecipeController {

    // now we use the service instead of the repository
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping
    @RequestMapping("/recipe/{id}/show")
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

    // handle post request, @ModelAttribute tell spring to bind form post parameters to recipecommand object
    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        // service returns back a new implementation of the command
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        log.debug("this recipe is ok");
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
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



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");// this is the html page
        modelAndView.addObject("exception", exception);
        //add exception attribute to the model
        //different from: model.addAttribute(String, Object) states Add the supplied attribute under the supplied name.

        return modelAndView;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadRequest(Exception exception){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("400error");// this is the html page
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
