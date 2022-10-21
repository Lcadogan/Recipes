package com.example.persistence;

import com.example.businesslayer.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Создаем уровень сохранения, который бедет взаимодействовать
 *  с нашим уровенем базы данных. Мы реализуем наш слой используя @Repository и @CrudRepository.
 *  Это позволит нам создавать базовые  CRUD-запросы для нашей базы данных.
 */

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findRecipeById(long id);
    List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name);
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
}
