package recipes.casestudy.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import recipes.casestudy.database.entity.Ingredient;
import recipes.casestudy.database.entity.Recipe;
import recipes.casestudy.database.entity.RecipeIngredient;

import java.util.List;

@Repository
public interface RecipeIngredientDAO extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> getRecipeIngredientByRecipe(Recipe recipe);

    RecipeIngredient findById(Integer id);

    @Modifying
    @Transactional
    int deleteById(Integer id);

    RecipeIngredient getRecipeIngredientByRecipeAndIngredient(Recipe recipe, Ingredient ingredient);
}
