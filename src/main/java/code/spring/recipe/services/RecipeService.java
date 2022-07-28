package code.spring.recipe.services;

import code.spring.recipe.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipe();
}
