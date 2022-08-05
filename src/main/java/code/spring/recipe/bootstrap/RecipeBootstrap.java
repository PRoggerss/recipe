package code.spring.recipe.bootstrap;

import code.spring.recipe.domain.*;
import code.spring.recipe.repositories.CategoryRepository;
import code.spring.recipe.repositories.RecipeRepository;
import code.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        recipeRepository.saveAll(getRecipe());
        log.debug("Loading Bootstrap Data");

    }


    private List<Recipe> getRecipe(){

        List<Recipe> recipes = new ArrayList<>(2);
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }
        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }
        UnitOfMeasure eachMeasure = eachUomOptional.get();
        UnitOfMeasure tableMeasure = tableSpoonUomOptional.get();

        Optional<Category> americaCategoryOptional = categoryRepository.findByDescription("American");
        if (!americaCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category not found");
        }
        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
        if (!italianCategoryOptional.isPresent()){
            throw new RuntimeException("Ecpected Category Not Found");
        }
        Category americanCategory  = americaCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();

        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDirections("dmsmdlsnnssm");
        Notes guacNote = new Notes();
        guacNote.setRecipeNotes("Is very very EASY....NOOOO!!!!");
        //guacNote.setRecipe(guacamoleRecipe);

        guacamoleRecipe.setNote(guacNote);
        guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2),eachMeasure));
        guacamoleRecipe.addIngredient(new Ingredient("no saber que poner", new BigDecimal(3),tableMeasure));
        guacamoleRecipe.getCategories().add(americanCategory);

        Recipe pizzaRecipe = new Recipe();
        pizzaRecipe.getCategories().add(americanCategory);
        pizzaRecipe.getCategories().add(italianCategory);
        pizzaRecipe.setCookTime(1);
        pizzaRecipe.setPrepTime(30);
        pizzaRecipe.setDifficulty(Difficulty.MODERATE);
        pizzaRecipe.setDescription("Pizza Italian");
        pizzaRecipe.setDirections("1.Masa grande estirada y no se que mas poner");
        Notes italNote = new Notes();
        italNote.setRecipeNotes("Tiene pocos ingredientes");
       // italNote.setRecipe(pizzaRecipe);
        pizzaRecipe.setNote(italNote);
        pizzaRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2),eachMeasure));
        pizzaRecipe.addIngredient(new Ingredient("no saber que poner", new BigDecimal(3),tableMeasure));




        recipes.add(guacamoleRecipe);
        recipes.add(pizzaRecipe);



        return recipes;


    }


}
