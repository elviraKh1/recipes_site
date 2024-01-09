package recipes.casestudy.database.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import recipes.casestudy.database.entity.Recipe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipeDAOTest {

    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private UserDAO userDAO;

    @CsvSource({"Chiken pie , Ullamco laboris nisi ut aliquip ex ea commodo consequat, test.jpg ,Breakfast ,08/01/2024",
                "Sweet Mustard Pecan Salmon , Preheat Oven to 400 degrees Step 2 Line a baking pan with parchment paper, test3.jpg ,Lunch ,09/01/2024",})
    @ParameterizedTest
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
        Assertions.assertEquals(name, recipe.getName());
        Assertions.assertEquals(instructions, recipe.getInstructions());
        Assertions.assertEquals(imageUrl, recipe.getImageUrl());
        Assertions.assertEquals(category, recipe.getCategory());
        Assertions.assertEquals(date, recipe.getCreateDate());
        Assertions.assertEquals(authorId, recipe.getAuthorId());
    }


    @ParameterizedTest
    @CsvSource({"Chiken pie ,Ullamco laboris nisi ut aliquip ex ea commodo consequat",
                "Sweet Mustard Pecan Salmon , Preheat Oven to 400 degrees Step 2 Line a baking pan with parchment paper"})
    @Order(2)
    public void findRecipeByByNameAndInstructionsTest(String name, String instructions) {
        //when
        List<Recipe> recipes = recipeDAO.findByNameAndInstructions(name, instructions);

        //then
        Assertions.assertEquals(1, recipes.size());

        Recipe recipe = recipes.get(0);
        Assertions.assertEquals(name, recipe.getName());
        Assertions.assertEquals(instructions, recipe.getInstructions());
    }


    @ParameterizedTest
    @CsvSource({"Chiken pie, Ullamco laboris nisi ut aliquip ex ea commodo consequat",
            "Sweet Mustard Pecan Salmon , Preheat Oven to 400 degrees Step 2 Line a baking pan with parchment paper"})
    @Order(3)
    public void deleteRecipeByNameAndInstructionsTest(String name, String instructions) {
        List<Recipe> recipes = recipeDAO.findByNameAndInstructions(name, instructions);

        //then
        Assertions.assertEquals(1, recipes.size());
        Recipe recipe = recipes.get(0);

        int deletedRow = recipeDAO.deleteById(recipe.getId());
        Assertions.assertEquals(1, deletedRow);
    }

    @ParameterizedTest
    @CsvSource({"Chiken pie, Ullamco laboris nisi ut aliquip ex ea commodo consequat",
            "Sweet Mustard Pecan Salmon , Preheat Oven to 400 degrees Step 2 Line a baking pan with parchment paper"})
    @Order(4)
    public void shouldNotExistRecipeTest(String name, String instructions) {
        //when
        List<Recipe> recipes = recipeDAO.findByNameAndInstructions(name, instructions);

        //then
        Assertions.assertEquals(0, recipes.size());
    }
}
