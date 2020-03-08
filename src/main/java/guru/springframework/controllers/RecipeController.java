package guru.springframework.controllers;


import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.ICommand;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


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
        RecipeCommand newRecipeCommand = new RecipeCommand();
        model.addAttribute("recipe", newRecipeCommand);
        return "recipe/recipeform";
    }


    // The @ModelAttribute is an annotation that binds a method parameter or method return value to a named model attribute and then exposes it to a web view.
    // add binding error
    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "recipe/recipeform";
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }



    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommand(Long.valueOf(id)));
        return "recipe/recipeform";
    }


    @GetMapping
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
