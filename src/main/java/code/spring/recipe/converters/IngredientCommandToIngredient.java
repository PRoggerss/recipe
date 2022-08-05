package code.spring.recipe.converters;

import code.spring.recipe.commands.IngredientCommand;
import code.spring.recipe.commands.UnitOfMeasureCommand;
import code.spring.recipe.domain.Ingredient;
import code.spring.recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConvert;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConvert) {
        this.uomConvert = uomConvert;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null){
            return null;
        }

        Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        if(source.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitOfMeasure(uomConvert.convert(source.getUom()));

        return ingredient;
    }
}
