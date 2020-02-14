package guru.springframework.controllers;

import guru.springframework.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jt on 6/1/17.
 */
@Controller
public class IndexController {


    // now we use the service instead of the repository
    private final RecipeService recipeService;


    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String setIndexPage(Model model){
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";

    }
}

 /*
    // we are going to do constructor based dependency injection

    // why do we only create repo for cate, recipe and unitofmeausre???
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

   //Note we do constructor dependency injection!!!!! command n, this is going to wire the repo in for us!!!!!!

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    // we can hit "", "/", "/index" to get to the page
    public String getIndexPage()
    {
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American"); // note here we have set the object!!
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("cat id is" + categoryOptional.get().getId()); // note here do get first then getid
        System.out.println("UOM id is" + unitOfMeasureOptional.get().getId());

        //cat id is 1, UOM id is1 is printed out!!!! once we visited the index page!!!

        return "index"; // the thymelyf index page will be called and the sp
    }
}
*/



/*
Assignment:
1. use the recipes from the website
2. add needed unit of measure in data.sql
3. use bootstrap class to create recipes on startup
4. create service to return recipe list to controller
5. pass list to thymeleaf to view the display on index page
 */