package code.spring.recipe.controllers;

import code.spring.recipe.commands.RecipeCommand;
import code.spring.recipe.exceptions.NotFoundException;
import code.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController{
    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipefomr";

    private final RecipeService recipeService;

    public RecipeController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showId(@PathVariable final String id, final Model model){
        model.addAttribute("recipe", this.recipeService.findById(Long.valueOf(id)));

        return "recipe/show";

    }
    //para mostrar la ventana de formulario de recetas

    @RequestMapping("recipe/new")
    public String newRecipe(final Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") final RecipeCommand command, final BindingResult bindingResult){
        //verifica si hay errores
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        final RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);
        //redirect le dice a spring que se redijia a esa pagina
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable final String id){
        log.debug("deleting id:"+ id);
        this.recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
    //Control de errores

    @ResponseStatus(HttpStatus.NOT_FOUND)//devuelve el estado de respuesta del http- error 404
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(final Exception exception){
        log.error("Handling not found exception");
        log.error(exception.getMessage());


        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }


}
