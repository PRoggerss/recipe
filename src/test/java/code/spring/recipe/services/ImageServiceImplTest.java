package code.spring.recipe.services;

import code.spring.recipe.commands.RecipeCommand;
import code.spring.recipe.domain.Recipe;
import code.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.OptionalLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    void saveImageFile() throws IOException {
        //given
        Long id = 1L;
        MultipartFile multipartFile = new MockMultipartFile("imagefile","testing.txt", "text/plain", "Spring boot".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe>  recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        //devuelve un valor nulo
        imageService.saveImageFile(id, multipartFile);

        //then
        //se captura el argumento
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        //se verifica el tamaño en bytes
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }


}