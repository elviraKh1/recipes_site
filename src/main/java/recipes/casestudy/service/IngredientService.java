package recipes.casestudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.casestudy.database.dao.IngredientDAO;
import recipes.casestudy.database.entity.Ingredient;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.formbean.IngredientFormBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;

@Slf4j
@Service
public class IngredientService {

    @Autowired
    private IngredientDAO ingredientDAO;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public Ingredient addIngredient(IngredientFormBean form) {
        log.debug("debug IngredientFormBean  =" + form);

        Ingredient ingredient = ingredientDAO.findById(form.getId());

        if (ingredient == null) {
            ingredient = new Ingredient();
        }
        ingredient.setName(form.getName());

        return ingredientDAO.save(ingredient);
    }
}