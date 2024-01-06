package recipes.casestudy.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.casestudy.database.entity.Ingredient;
import recipes.casestudy.database.entity.Recipe;
import recipes.casestudy.database.entity.RecipeIngredient;

import java.util.List;

@Repository
public interface RecipeIngredientDAO extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> getRecipeIngredientByRecipe(Recipe recipe);
    RecipeIngredient getRecipeIngredientByRecipeAndIngredient(Recipe recipe, Ingredient ingredient);
}
