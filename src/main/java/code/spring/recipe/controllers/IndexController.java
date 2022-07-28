package code.spring.recipe.controllers;

import code.spring.recipe.domain.Category;
import code.spring.recipe.domain.UnitOfMeasure;
import code.spring.recipe.repositories.CategoryRepository;
import code.spring.recipe.repositories.UnitOfMeasureRepository;
import code.spring.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){
        model.addAttribute("recipes", recipeService.getRecipe());

        return "index";
    }
}
