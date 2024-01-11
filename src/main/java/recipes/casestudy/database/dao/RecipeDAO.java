package recipes.casestudy.database.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import recipes.casestudy.database.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe, Long> {

    Recipe findById(Integer id);

    @Modifying
    @Transactional
    int deleteById(Integer id);

    @Query("SELECT DISTINCT r FROM Recipe r "+
    " LEFT JOIN RecipeIngredient  ri ON r.id = ri.recipe.id "+
    " LEFT JOIN Ingredient i ON ri.ingredient.id = i.id WHERE "+
    " LOWER(i.name) LIKE :word     OR LOWER(r.name) LIKE :word OR LOWER(r.instructions) LIKE :word")
    Page<Recipe> findByNameOrInstructions(String word, Pageable pageable);

    @Query(nativeQuery = true, value = "select r.* from recipes r where r.name like :name and r.instructions like :instructions ")
    List<Recipe> findByNameAndInstructions(String name, String instructions);

    Page<Recipe> findByAuthorId(Integer authorId, Pageable pageable);

    @Query("SELECT r FROM Recipe r , BookmarkRecipe br where br.recipe.id = r.id and br.user.id = :userId ")
    Page<Recipe> findByBookmarkRecipeAAndUser(Integer userId, Pageable pageable);


    @Query(nativeQuery = true, value ="select r.*  from Recipes r, Recipe_Ingredients ri where r.id = ri.recipe_id and ri.ingredient_id = :id")
    Page<Recipe> findRecipeByIngredientId(Integer id, Pageable pageable);

    @Query("SELECT q FROM Recipe q where q.category = :category")
    Page<Recipe> findByCategoryIgnoreCase(String category, Pageable pageable);

    Page<Recipe> findAll(Pageable pageable);
}
