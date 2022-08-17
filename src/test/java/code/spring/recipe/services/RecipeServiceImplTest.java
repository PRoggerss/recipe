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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        final Recipe recipe = new Recipe();
        final HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(this.recipeRepository.findAll()).thenReturn(recipeData);
        final Set<Recipe> recipes = this.recipeService.getRecipe();

        assertEquals(recipes.size(), 1);
        //estamos diciendo que se llame al repositorio una sola vez
        verify(this.recipeRepository, times(1)).findAll();
                
    }

    @Test
    void getRecipeIdTest(){
        final Recipe recipe= new Recipe();
        recipe.setId(1L);
        final Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        final Recipe recipeReturned = this.recipeService.findById(1L);

        assertNotNull(recipeReturned);
    }

    @Test
    void deleteRecipeTest(){
        //given
        final Long idToDelete = Long.valueOf(2L);
        //when
        this.recipeService.deleteById(idToDelete);

        //no se utiliza when porque no retorna nada
        //then
        verify(this.recipeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void getRecipeByIdTestNotFound() throws Exception {

        final Optional <Recipe> recipeOptional = Optional.empty();
        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        final Recipe recipeReturned = this.recipeService.findById(1L);
    }



}