package guru.springframework.controllers;


import guru.springframework.domain.Recipe;
import guru.springframework.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // below is the setup method
        controller = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        // we don't have to bring up the whole springcontext
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() throws Exception{

        // given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());


        Recipe recipe = new Recipe();
        recipe.setId(1L);

        recipes.add(recipe);

        //when
        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        // note here we have


        // then, note pass model into it
        String viewName = controller.setIndexPage(model);
        assertEquals(viewName, "index");

        /*Mockito.verify(MockedObject).someMethodOnTheObject(someParametersToTheMethod);
         verifies that the methods you called on your mocked object are indeed called.
          If they weren't called, or called with the wrong parameters,
         or called the wrong number of times, they would fail your test.
         */
        verify(recipeService, times(1)).getRecipes();
        // If we want to use a specific value for an argument, then we can use eq() method.
        // Capture the argument of addAttribute method
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        // Assert the argument
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());

    }


}
