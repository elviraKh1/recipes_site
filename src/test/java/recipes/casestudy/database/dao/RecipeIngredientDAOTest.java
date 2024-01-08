package recipes.casestudy.database.dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import recipes.casestudy.database.entity.Ingredient;
import recipes.casestudy.database.entity.Recipe;
import recipes.casestudy.database.entity.RecipeIngredient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeIngredientDAOTest {
    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private RecipeIngredientDAO recipeIngredientDAO;

    @Autowired
    private IngredientDAO ingredientDAO;

    @Autowired
    private UserDAO userDAO;

    @ParameterizedTest
    @CsvSource({"Chiken pie ,Ullamco laboris nisi ut aliquip ex ea commodo consequat, test.jpg ,Breakfast ,08/01/2024"})
    @Order(1)
    public void createRecipeTest(String name, String instructions, String imageUrl, String category, String dateStr) throws ParseException {
        //given
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        Integer authorId = userDAO.findByEmailIgnoreCase("admin@admin.admin").getId();

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setCreateDate(date);
        recipe.setCategory(category);
        recipe.setInstructions(instructions);
        recipe.setImageUrl(imageUrl);
        recipe.setAuthorId(authorId);

        //when
        recipe = recipeDAO.save(recipe);

       //then
        Assertions.assertNotNull(recipe.getId());
    }
    @ParameterizedTest
    @CsvSource({"Chiken pie ,Ullamco laboris nisi ut aliquip ex ea commodo consequat, water, 2 cups"})
    @Order(2)
    public void createRecipeIngredientTest(String name, String instructions, String ingredientName1,  String measure1) {
        //given
        List<Recipe> recipes = recipeDAO.findByNameAndInstructions(name , instructions);
        Recipe recipe = recipes.get(0);

        //then
        RecipeIngredient recipeIngredient1 = new RecipeIngredient();
        List<Ingredient> ingredients1 = ingredientDAO.findByNameLikeIgnoreCase(ingredientName1);
        Ingredient ingredient1 = ingredients1.get(0);
        recipeIngredient1.setIngredient(ingredient1);
        recipeIngredient1.setMeasure(measure1);
        recipeIngredient1.setRecipe(recipe);
        recipeIngredientDAO.save(recipeIngredient1);

        List<RecipeIngredient> actualRecipeIngredients=recipeIngredientDAO.getRecipeIngredientByRecipe(recipe);

        RecipeIngredient actualRecipeIngredient1 = actualRecipeIngredients.get(0);

        Assertions.assertEquals(1, actualRecipeIngredients.size());
        Assertions.assertEquals(ingredient1, actualRecipeIngredient1.getIngredient());
        Assertions.assertEquals(measure1, actualRecipeIngredient1.getMeasure());

    }

    @ParameterizedTest
    @CsvSource({"Chiken pie ,Ullamco laboris nisi ut aliquip ex ea commodo consequat"})
    @Order(3)
    public void deleteAllRecipeIngredientByRecipe(String name, String instructions) {
        List<Recipe> recipes = recipeDAO.findByNameAndInstructions(name , instructions);
        Recipe recipe = recipes.get(0);

        List<RecipeIngredient> recipesIngredients = recipeIngredientDAO.getRecipeIngredientByRecipe(recipe);
        //then
        recipesIngredients.clear();

        recipe.setRecipeIngredients(recipesIngredients);
        recipe = recipeDAO.save(recipe);

        List<RecipeIngredient> actualRecipesIngredients = recipeIngredientDAO.getRecipeIngredientByRecipe(recipe);
        Assertions.assertNull(actualRecipesIngredients);
    }

    @ParameterizedTest
    @CsvSource({"Chiken pie ,Ullamco laboris nisi ut aliquip ex ea commodo consequat"})
    @Order(4)
    public void deleteRecipeByNameAndInstructionsTest(String name, String instructions) {
        List<Recipe> recipes = recipeDAO.findByNameAndInstructions(name , instructions);
        Recipe recipe = recipes.get(0);

        int cnt = recipeDAO.deleteById(recipe.getId());

        Assertions.assertEquals(1 , cnt);
    }
}
