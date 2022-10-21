package com.example.businesslayer;

import com.example.persistence.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service действует как посредник между бизнес-уровнем и уровнем persistence(сохраняемости)
 *  Service будет применять бизнес-правила, а затем перенапровлять запрос на уровень persistence для
 *  монипулирования базой данных по мере необходимости.
 */

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeById(Long id) {
        return  recipeRepository.findRecipeById(id);
    }

    public Recipe save(Recipe toSave) {
        return recipeRepository.save(toSave);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name) {
       return recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
    }

    public List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category) {
        return  recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }
}
