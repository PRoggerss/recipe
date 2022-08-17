package code.spring.recipe.controllers;

import code.spring.recipe.exceptions.NotFoundException;
import code.spring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class RecipeControllerTest {
    @Mock
    RecipeService recipeService;
    RecipeController controller;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        this.controller = new RecipeController(this.recipeService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();

    }

    @Test
    void showId() {
    }

    @Test
    void newRecipe() {
    }

    @Test
    void saveOrUpdate() {
    }

    @Test
    void deleteById() {
    }


    @Test
    public void testGetRecipeNotFound() throws Exception {
        when(this.recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        this.mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void testGetRecipeNumberFormatException() throws Exception{
        this.mockMvc.perform(get("/recipe/asf/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("404error"));
    }
}