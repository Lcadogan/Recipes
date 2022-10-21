package com.example.presentation;

import com.example.businesslayer.Recipe;
import com.example.businesslayer.RecipeService;
import com.example.persistence.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * Уровень presentation предоставляет возможность конечному пользователю взаимодействовать
 * с приложением.Мы будем использовать RecipeService для отправки запросов в базу данных для
 * извлечения и изменения данных.
 */

@RestController
public class RecipeController {

    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipe/new")
    public Map saveRecipe(@Valid @RequestBody Recipe recipe,
                          @AuthenticationPrincipal UserDetailsImpl details) {
        Recipe createdRecipe = recipeService.save(new Recipe(recipe.getId(), details.getUser(), recipe.getName(),
                recipe.getCategory(), LocalDateTime.now(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections()));
        return Map.of("id", createdRecipe.getId());
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> putRecipe(@Valid @RequestBody Recipe recipe,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Recipe updateRecipe = recipeService.findRecipeById(id);
        if (updateRecipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!updateRecipe.getAuthor().equals(userDetails.getUser())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        updateRecipe.setId(id);
        updateRecipe.setName(recipe.getName());
        updateRecipe.setCategory(recipe.getCategory());
        updateRecipe.setDate(LocalDateTime.now());
        updateRecipe.setDescription(recipe.getDescription());
        updateRecipe.setIngredients(recipe.getIngredients());
        updateRecipe.setDirections(recipe.getDirections());

        recipeService.save(updateRecipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.findRecipeById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return recipe;
        }
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> getSearch(@RequestParam (value = "category", required = false) String category,
                                  @RequestParam (value = "name", required = false) String name) {
       if (category == null && name == null || category != null && name != null ) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
       }
       if (category != null) {
           return recipeService.findByCategoryIgnoreCaseOrderByDateDesc(category);
       }
       if (name != null) {
           return recipeService.findByNameIgnoreCaseContainsOrderByDateDesc(name);
       }
       return List.of();
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Recipe recipe = recipeService.findRecipeById(id);

        if (recipe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (recipe.getAuthor().equals(userDetails.getUser())) {
                recipeService.deleteById(id);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
