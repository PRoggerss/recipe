package code.spring.recipe.controllers;

import code.spring.recipe.commands.RecipeCommand;
import code.spring.recipe.services.ImageService;
import code.spring.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    ImageController controller;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        //se injecta el servicio de imagen
        controller = new ImageController(imageService, recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getImageFormTest() throws Exception{
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //aqui se le dice a mockito que cuando se haga busqueda por id presentar eso
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        //aqui se prueba el spring MVC..obtencion de receta en base al id
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));


    }

    @Test
    public void imagePostTest() throws Exception {
        //se crea un archivo multiparte simulado
        MockMultipartFile multipartFile =
                new MockMultipartFile("file","testing.txt","text/plain",
                        "Spring framework".getBytes());
        //aqui se espera una nueva redireccion
        //se sube el archivo de imagen, se almacena y luego se redirecciona a la pagina mostrar receta
        this.mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));
    }
}