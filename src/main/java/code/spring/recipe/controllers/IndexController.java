package code.spring.recipe.controllers;

import code.spring.recipe.domain.Category;
import code.spring.recipe.domain.UnitOfMeasure;
import code.spring.recipe.repositories.CategoryRepository;
import code.spring.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        System.out.println("Cat ID is: "+ categoryOptional.get().getId());
        System.out.println("UOM ID is: "+ unitOfMeasureOptional.get().getId());
        return "index";
    }
}
