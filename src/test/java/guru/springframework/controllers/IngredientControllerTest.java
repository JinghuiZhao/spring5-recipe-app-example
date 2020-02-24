package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController controller;

    MockMvc mockMvc;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Before
    public void setUp() throws Exception {
        // note that we need to initiate controller
        MockitoAnnotations.initMocks(this);
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

        //later we add ingredientService
        // we wanna test IngredientController in isolation
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // need setup otherwise it will throw Test class should have exactly one public zero-argument constructor error

    @Test
    public void testIngredientList() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findRecipeCommand(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                // note the ingredient template should be under recipe folder
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findRecipeCommand(anyLong());
    }

    @Test
    public void testIngredientShow() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientService.findRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);


        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                // note the ingredient template should be under recipe folder
                .andExpect(model().attributeExists("ingredient"));
        // note here this is attribute of ingredient

    }


    @Test
    public void testUpdateIngredient() throws Exception {

        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //when
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                // note the ingredient template should be under recipe folder
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList")); // note here also expect uomlist

        //then
        //verify(ingredientService, times(1)).findRecipeIdAndIngredientId(anyLong(), anyLong());
    }


    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        //when
        when(ingredientService.saveIngredient(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

}
