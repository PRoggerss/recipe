package code.spring.recipe.controllers;

import code.spring.recipe.domain.Recipe;
import code.spring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class IngredientCommandControllerTest {



    @Mock
    RecipeService recipeService;
    RecipeController controller;
    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void getRecipeTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);



        when(recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/show/1"))//se hace una salida a esa url y se espera que suceda lo siguiente
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }
    @Test
    public void deleteTest() throws Exception{
        mockMvc.perform(get("recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect/"));
        verify(recipeService, times(1)).deleteById(anyLong());
    }

}