package code.spring.recipe.services;

import code.spring.recipe.commands.IngredientCommand;
import code.spring.recipe.converters.IngredientCommandToIngredient;
import code.spring.recipe.converters.IngredientToIngredientCommand;
import code.spring.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import code.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import code.spring.recipe.domain.Ingredient;
import code.spring.recipe.domain.Recipe;
import code.spring.recipe.repositories.IngredientRepository;
import code.spring.recipe.repositories.RecipeRepository;
import code.spring.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.criteria.CriteriaBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {
    private final IngredientToIngredientCommand toIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    IngredientRepository ingredientRepository;

    IngredientService ingredientService;
    public IngredientServiceImplTest() {
        toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient= new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(toIngredientCommand, recipeRepository, unitOfMeasureRepository, ingredientCommandToIngredient, ingredientRepository);
    }


    @Test
    void findByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3= new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        //then
        IngredientCommand ingredientCommand= ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
        assertEquals(Long.valueOf(3L),ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());

        verify(recipeRepository, times(1)).findById(anyLong());
    }
}