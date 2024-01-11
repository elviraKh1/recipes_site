package recipes.casestudy.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import recipes.casestudy.database.entity.BookmarkRecipe;
import recipes.casestudy.database.entity.Recipe;
import recipes.casestudy.database.entity.User;

@Repository
public interface BookmarkRecipeDAO extends JpaRepository<BookmarkRecipe, Long> {

    @Modifying
    @Transactional
    int deleteById(Integer id);

    BookmarkRecipe getBookmarkRecipeByRecipeAndUser(Recipe recipe, User user);

}