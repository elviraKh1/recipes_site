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

    private Recipe recipe;
    private Ingredient ingredient;
    private final String ingredientMeasure = "0.5 tbs";
    private String ingredientName = "hkjhkhkhkhkhhh";

    @ParameterizedTest
    @CsvSource({"Chiken pie , Ullamco laboris nisi ut aliquip ex ea commodo consequat, test.jpg ,Breakfast ,08/01/2024"})
    @Order(1)
    public void createRecipeIngredientTest(String name, String instructions, String imageUrl, String category, String dateStr) throws ParseException {
        //given


        recipe = createRecipe(name, instructions, imageUrl, category, dateStr);
        ingredient = createIngredient(ingredientName);

        Assertions.assertNotNull(recipe.getId());
        Assertions.assertNotNull(ingredient.getId());

        //when
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setMeasure(ingredientMeasure);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient = recipeIngredientDAO.save(recipeIngredient);

//        RecipeIngredient actualRecipeIngredient = recipeIngredientDAO.findById(recipeIngredient1.getId());
        Assertions.assertNotNull(recipeIngredient.getId());
        Assertions.assertEquals(ingredient, recipeIngredient.getIngredient());
        Assertions.assertEquals(ingredientMeasure, recipeIngredient.getMeasure());
    }

    @ParameterizedTest
    @CsvSource({"Chiken pie, Ullamco laboris nisi ut aliquip ex ea commodo consequat"})
    @Order(2)
    public void findRecipeIngredient(String name, String instructions) {
        //when
        List<Recipe> recipe = recipeDAO.findByNameAndInstructions(name, instructions);
        List<Ingredient> ingredient = ingredientDAO.findByNameLikeIgnoreCase(ingredientName);

        RecipeIngredient recipeIngredient =
                recipeIngredientDAO.getRecipeIngredientByRecipeAndIngredient(recipe.get(0), ingredient.get(0));

        //then
        Assertions.assertNotNull(recipeIngredient.getId());
    }

    @ParameterizedTest
    @CsvSource({"Chiken pie ,Ullamco laboris nisi ut aliquip ex ea commodo consequat"})
    @Order(3)
    public void deleteAllRecipeIngredient(String name, String instructions) {
        List<Recipe> recipe = recipeDAO.findByNameAndInstructions(name, instructions);
        List<Ingredient> ingredient = ingredientDAO.findByNameLikeIgnoreCase(ingredientName);

        RecipeIngredient recipeIngredient =
                recipeIngredientDAO.getRecipeIngredientByRecipeAndIngredient(recipe.get(0), ingredient.get(0));

        int c = recipeIngredientDAO.deleteById(recipeIngredient.getId());

        Assertions.assertEquals(1, c);
    }

    @ParameterizedTest
    @CsvSource({"Chiken pie, Ullamco laboris nisi ut aliquip ex ea commodo consequat"})
    @Order(4)
    public void shouldNotExistRecipeTest(String name, String instructions) {
        //when
        List<Recipe> recipe = recipeDAO.findByNameAndInstructions(name, instructions);
        List<Ingredient> ingredient = ingredientDAO.findByNameLikeIgnoreCase(ingredientName);

        RecipeIngredient recipeIngredient =
                recipeIngredientDAO.getRecipeIngredientByRecipeAndIngredient(recipe.get(0), ingredient.get(0));

        Assertions.assertNull(recipeIngredient);
    }

    @ParameterizedTest
    @CsvSource({"Chiken pie, Ullamco laboris nisi ut aliquip ex ea commodo consequat"})
    @Order(5)
    public void deleteTestDataTest(String name, String instructions){
    //when
        List<Recipe> recipe = recipeDAO.findByNameAndInstructions(name, instructions);
        List<Ingredient> ingredient = ingredientDAO.findByNameLikeIgnoreCase(ingredientName);

        int c1 = recipeDAO.deleteById(recipe.get(0).getId());
        int c2 = ingredientDAO.deleteById(ingredient.get(0).getId());
        //then
        Assertions.assertEquals(1, c1);
        Assertions.assertEquals(1, c2);
    }

    private Ingredient createIngredient(String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        return ingredientDAO.save(ingredient);
    }

    private Recipe createRecipe(String name, String instructions, String imageUrl, String category, String dateStr) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        Integer authorId = userDAO.findByEmailIgnoreCase("admin@admin.admin").getId();

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setCreateDate(date);
        recipe.setCategory(category);
        recipe.setInstructions(instructions);
        recipe.setImageUrl(imageUrl);
        recipe.setAuthorId(authorId);
        return recipeDAO.save(recipe);
    }
}
