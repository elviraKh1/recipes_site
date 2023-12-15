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

    @Query("select r from Recipe r where r.name like :name or r.instructions like :instructions ")
    List<Recipe> findByText(String name, String instructions);

    List<Recipe> findByAuthorId(Integer authorId);
}
