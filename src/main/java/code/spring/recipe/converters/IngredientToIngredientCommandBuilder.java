package code.spring.recipe.converters;

import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommandBuilder {
    private UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommandBuilder setUomConverter(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
        return this;
    }

    public IngredientToIngredientCommand createIngredientToIngredientCommand() {
        return new IngredientToIngredientCommand(uomConverter);
    }
}