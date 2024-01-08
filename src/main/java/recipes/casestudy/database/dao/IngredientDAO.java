package recipes.casestudy.database.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import recipes.casestudy.database.entity.Ingredient;

import java.util.List;

@Repository
public interface IngredientDAO extends JpaRepository<Ingredient, Long> {

    Ingredient findById(Integer id);

    @Modifying
    @Transactional
    int deleteById(Integer id);

    List<Ingredient> findByNameLikeIgnoreCase(String name);

    Page<Ingredient> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
