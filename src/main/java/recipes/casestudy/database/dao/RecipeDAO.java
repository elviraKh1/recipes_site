package recipes.casestudy.database.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recipes.casestudy.database.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeDAO extends JpaRepository<Recipe, Long> {

    Recipe findById(Integer id);

    void deleteById(Integer id);

    @Query("select r from Recipe r where r.name like :word or r.instructions like :word ")
    Page<Recipe> findByNameOrInstructions(String word,Pageable pageable);

    List<Recipe> findByAuthorId(Integer authorId);
    Page<Recipe> findAll(Pageable pageable);
}
