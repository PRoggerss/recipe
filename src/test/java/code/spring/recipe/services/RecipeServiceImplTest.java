package code.spring.recipe.services;

import code.spring.recipe.domain.Recipe;
import code.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        //inicializar
        //esto le dice a mockito que me de un falso recipeRepository
        MockitoAnnotations.initMocks(this);
      //  recipeService = new RecipeServiceImpl(recipeRepository, recipeCommand, recipeToRecipeCommand);
    }

    @Test
    void getRecipeTest() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipe();

        assertEquals(recipes.size(), 1);
        //estamos diciendo que se llame al repositorio una sola vez
        verify(recipeRepository, times(1)).findAll();
                
    }

    @Test
    void getRecipeIdTest(){
        Recipe recipe= new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned);
    }

    @Test
    void deleteRecipeTest(){
        //given
        Long idToDelete = Long.valueOf(2L);
        //when
        recipeService.deleteById(idToDelete);

        //no se utiliza when porque no retorna nada
        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }


}