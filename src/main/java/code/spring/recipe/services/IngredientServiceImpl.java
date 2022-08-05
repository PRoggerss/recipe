package code.spring.recipe.services;

import code.spring.recipe.commands.IngredientCommand;
import code.spring.recipe.converters.IngredientCommandToIngredient;
import code.spring.recipe.converters.IngredientToIngredientCommand;
import code.spring.recipe.domain.Ingredient;
import code.spring.recipe.domain.Recipe;
import code.spring.recipe.repositories.IngredientRepository;
import code.spring.recipe.repositories.RecipeRepository;
import code.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, IngredientRepository ingredientRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long idRecipe, Long idIngredient) {
        Optional<Recipe> recipeOptional =recipeRepository.findById(idRecipe);
        if (!recipeOptional.isPresent()){
            log.error("recipe is not found Id"+ idRecipe);
        }
        Recipe recipe  = recipeOptional.get();
        //stream nos ayuda para crear un flujo de trabajo/datos para poder manejarlos
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(item -> item.getId().equals(idIngredient))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()){
            log.error("Ingredient id not found");
        }
        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (!recipeOptional.isPresent()){
            log.error("Recipe not found for id: "+ command.getRecipeId());
            return new IngredientCommand();//se devuelve una receta no encontrada
        }else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();
            if (ingredientOptional.isPresent()){//se verifica si existe y se actualiza
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository.findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));//todo poner de mejor manera porque es codigo fragil

            }else{

                //add new ingredient
                Ingredient newIngredient = ingredientCommandToIngredient.convert(command);
                newIngredient.setRecipe(recipe);
                recipe.addIngredient(newIngredient);
            }
            Recipe saveRecipe =recipeRepository.save(recipe);

            Optional<Ingredient> saveIngredientOptional = saveRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();
            if (!saveIngredientOptional.isPresent()){
                saveIngredientOptional = saveRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(saveIngredientOptional.get());

        }
    }

    @Override
    public void deleteByIdIngredient(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredients -> ingredients.getId().equals(id))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient deleteIngredient = ingredientOptional.get();
                deleteIngredient.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }

        }

    }


}
