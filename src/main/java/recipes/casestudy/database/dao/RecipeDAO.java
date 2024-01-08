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

    @Query("select r from Recipe r where LOWER(r.name) like :word or LOWER(r.instructions) like :word ")
    Page<Recipe> findByNameOrInstructions(String word, Pageable pageable);

    @Query(nativeQuery = true, value = "select r.* from recipes r where r.name like :name and r.instructions like :instructions ")
    List<Recipe> findByNameAndInstructions(String name, String instructions);

    List<Recipe> findByAuthorId(Integer authorId);

    @Query("SELECT q FROM Recipe q where q.category = :category")
    Page<Recipe> findByCategoryIgnoreCase(String category, Pageable pageable);

    Page<Recipe> findAll(Pageable pageable);
}
