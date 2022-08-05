package code.spring.recipe.services;

import code.spring.recipe.commands.IngredientCommand;
import code.spring.recipe.domain.Recipe;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long idRecipe, Long idIngredient);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteByIdIngredient(Long recipeId, Long id);
}
