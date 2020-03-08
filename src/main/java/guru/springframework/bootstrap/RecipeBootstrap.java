package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Slf4j helpful logs that will help you understand what errors are being triggered, and what information is being processed.
 */

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    //ContextRefreshedEvent On either initializing or refreshing the ApplicationContext

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    //we wouldn't see lazy initialization for many relationships
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
        log.debug("loading bootstrap data");
    }

    // above is the essential part where we return the recipes to the repository
    // and for this repository,  we have created the implementation of the service where the
    // this repository will be injected through the constructor,  and we convert the iterable into
    // a hashset and return that back to the index controller and assign that to a property on
    // the view model of recipes, and finally recipes were displayed via thymeleaf

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(3);

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> lbsUomOptional = unitOfMeasureRepository.findByDescription("lbs");

        if(!lbsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }


        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }


        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }


        //.get() to return the values of optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teapoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();
        UnitOfMeasure lbsUom = lbsUomOptional.get();


        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }


        Optional<Category> asianCategoryOptional = categoryRepository.findByDescription("Asian");

        if(!asianCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category asianCategory = asianCategoryOptional.get();
        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.Easy);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");



        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n");

        // add the set in recipe object to set id for notes object
        // to make it bidirectional
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredients(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredients(new Ingredient("Kosher salt", new BigDecimal(".5"), teapoonUom));
        guacRecipe.addIngredients(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredients(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredients(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        guacRecipe.addIngredients(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredients(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
        guacRecipe.addIngredients(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        //add to return list
        recipes.add(guacRecipe);



        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.Medium);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n");

        // Note that this is bidirectional
        tacosRecipe.setNotes(tacoNotes);
        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");



        tacosRecipe.addIngredients(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredients(new Ingredient("Dried Oregano", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredients(new Ingredient("Dried Cumin", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredients(new Ingredient("Sugar", new BigDecimal(1), teapoonUom));
        tacosRecipe.addIngredients(new Ingredient("Salt", new BigDecimal(".5"), teapoonUom));
        tacosRecipe.addIngredients(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom));
        tacosRecipe.addIngredients(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom));
        tacosRecipe.addIngredients(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom));
        tacosRecipe.addIngredients(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredients(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom));
        tacosRecipe.addIngredients(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredients(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom));
        tacosRecipe.addIngredients(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom));
        tacosRecipe.addIngredients(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredients(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        tacosRecipe.addIngredients(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom));
        tacosRecipe.addIngredients(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredients(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom));
        tacosRecipe.addIngredients(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);


        // note that we saved categories of the recipe we can add this recipes to the list
        recipes.add(tacosRecipe);



        //Three Teacup Chicken Recipe
        Recipe chickenRecipe = new Recipe();
        chickenRecipe.setDescription("Three Teacup Chicken");
        chickenRecipe.setCookTime(9);
        chickenRecipe.setPrepTime(20);
        chickenRecipe.setDifficulty(Difficulty.Medium);

        chickenRecipe.setDirections("1 Make the braising liquid: In a bowl combine the soy sauce, rice vinegar, sugar, and Shaoxing wine together and mix well. Set aside.\n" +
                "\n" +
                "2 Sauté garlic, ginger, scallions, peppercorns: Heat a wok or large sauté pan on high heat until a drop of water will evaporate within 1-2 seconds. \n" +
                "Place the oil in the wok and swirl. Add the garlic, ginger, scallions, and Sichuan peppercorns/chili pepper if using and stir-fry for 30 seconds or until fragrant.\n" +
                "\n" +
                "3 Add the chicken and stir-fry until no pink on the surface remains (you just want to sear the outside nicely).\n" +
                "\n" +
                "4 Add the braising liquid and star anise or anise seed if using and cook for 1 minute ensuring that the chicken is well coated in the braising liquid.\n" +
                "\n" +
                "5 Add the water and cover and cook for 4 more minutes or until the chicken is cooked through and no pink remains.\n" +
                "\n" +
                "6 Serve immediately over rice with hearty amounts of the broth. Garnish with a little extra chopped scallion.");


        Notes chickenNotes = new Notes();
        chickenNotes.setRecipeNotes("This recipe is a simple braised chicken dish called Three Teacup Chicken." +
                " The name comes from the basic preparation in which a teacup is traditionally used to measure the three key " +
                "ingredients of the braising liquid—sugar, soy sauce, and rice vinegar.");

        // Note that this is bidirectional
        chickenRecipe.setNotes(chickenNotes);

        chickenRecipe.addIngredients(new Ingredient("soy sauce", new BigDecimal(0.25), cupsUom));
        chickenRecipe.addIngredients(new Ingredient("rice vinegar", new BigDecimal(0.25), cupsUom));
        chickenRecipe.addIngredients(new Ingredient("sugar", new BigDecimal(0.25), cupsUom));
        chickenRecipe.addIngredients(new Ingredient("Shaoxing wine or dry sherry", new BigDecimal( 1), tableSpoonUom));
        chickenRecipe.addIngredients(new Ingredient("sesame oil", new BigDecimal(1), tableSpoonUom));
        chickenRecipe.addIngredients(new Ingredient("scallions", new BigDecimal(6), eachUom));
        chickenRecipe.addIngredients(new Ingredient("chicken breast or thighs", new BigDecimal(6), lbsUom));

        chickenRecipe.getCategories().add(asianCategory);

        chickenRecipe.setUrl("https://www.simplyrecipes.com/recipes/three_teacup_chicken/");
        chickenRecipe.setServings(4);
        chickenRecipe.setSource("Simply Recipes");

        recipes.add(chickenRecipe);

        return recipes;
    }
}




        



