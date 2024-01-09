package recipes.casestudy.database.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import recipes.casestudy.database.entity.Ingredient;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IngredientDAOTest {

    @Autowired
    IngredientDAO ingredientDAO;

    @CsvSource({"aaaaaaaaa",
            "sssssssss",})
    @ParameterizedTest
    @Order(1)
    public void createIngredientTest(String name) {
        //given

        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);

        //when
        ingredient = ingredientDAO.save(ingredient);

        //then
        Assertions.assertNotNull(ingredient.getId());
        Assertions.assertEquals(name, ingredient.getName());
    }


    @ParameterizedTest
    @CsvSource({"aaaaaaaaa",
            "sssssssss",})
    @Order(2)
    public void findIngredientByName(String name) {
        //when
        List<Ingredient> ingredients = ingredientDAO.findByNameLikeIgnoreCase(name);

        //then
        Assertions.assertEquals(1, ingredients.size());

        Ingredient ingredient = ingredients.get(0);
        Assertions.assertEquals(name, ingredient.getName());
    }


    @ParameterizedTest
    @CsvSource({"aaaaaaaaa",
            "sssssssss",})
    @Order(3)
    public void deleteIngredientByNameTest(String name) {
        List<Ingredient> ingredients = ingredientDAO.findByNameLikeIgnoreCase(name);

        //then
        Assertions.assertEquals(1, ingredients.size());

        Ingredient ingredient = ingredients.get(0);

        int deletedRow = ingredientDAO.deleteById(ingredient.getId());
        Assertions.assertEquals(1, deletedRow);
    }

    @ParameterizedTest
    @CsvSource({"aaaaaaaaa",
            "sssssssss",})
    @Order(4)
    public void shouldNotExistIngredientTest(String name) {
        //when
        List<Ingredient> ingredients = ingredientDAO.findByNameLikeIgnoreCase(name);

        //then
        Assertions.assertEquals(0, ingredients.size());
    }
}
