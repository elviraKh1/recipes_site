package recipes.casestudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import recipes.casestudy.database.dao.RecipeDAO;
import recipes.casestudy.database.entity.Recipe;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.formbean.RecipeFormBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;

import java.util.Date;

@Slf4j
@Service
public class RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public Recipe addRecipe(RecipeFormBean form) {
        log.debug("debug RecipeFormBean  =" + form);

        Recipe recipe = recipeDAO.findById(form.getId());

        if (recipe == null) {
            recipe = new Recipe();
        }
        User user = authenticatedUserService.loadCurrentUser();
        recipe.setName(form.getName());
        recipe.setCategory(form.getCategory());
        recipe.setInstructions(form.getInstructions());
        recipe.setCreateDate(new Date());
        recipe.setImageUrl(form.getImageUrl());
        recipe.setAuthorId(user.getId());

        return recipeDAO.save(recipe);
    }

}
