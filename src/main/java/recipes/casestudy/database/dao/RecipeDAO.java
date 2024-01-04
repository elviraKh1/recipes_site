package recipes.casestudy.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recipes.casestudy.database.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe, Long> {

    Recipe findById(Integer id);

    int deleteById(Integer id);

    @Query("select r from Recipe r where r.name like :word or r.instructions like :word ")
    List<Recipe> findByNameOrInstructions(String word);

    List<Recipe> findByAuthorId(Integer authorId);

    List<Recipe> findAll();
}
