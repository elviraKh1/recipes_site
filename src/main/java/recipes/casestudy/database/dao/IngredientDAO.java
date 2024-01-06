package recipes.casestudy.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recipes.casestudy.database.entity.Ingredient;

import java.util.List;

@Repository
public interface IngredientDAO extends JpaRepository<Ingredient, Long> {

    Ingredient findById(Integer id);

    int deleteById(Integer id);

//    @Query("select i from Ingredient i where i.name like :name")
    List<Ingredient> findByNameLikeIgnoreCase(String name);

}
