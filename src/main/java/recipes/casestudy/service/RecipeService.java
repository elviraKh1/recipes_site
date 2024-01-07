package recipes.casestudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import recipes.casestudy.database.dao.IngredientDAO;
import recipes.casestudy.database.dao.RecipeDAO;
import recipes.casestudy.database.dao.RecipeIngredientDAO;
import recipes.casestudy.database.entity.Ingredient;
import recipes.casestudy.database.entity.Recipe;
import recipes.casestudy.database.entity.RecipeIngredient;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.formbean.RecipeFormBean;
import recipes.casestudy.formbean.RecipeIngredientFormBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private IngredientDAO ingredientDAO;

    @Autowired
    private RecipeIngredientDAO recipeIngredientDAO;
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

        List<RecipeIngredient> recipeIngredients = recipe.getRecipeIngredients();
        if (recipeIngredients == null || recipeIngredients.isEmpty()) {
            recipeIngredients = new ArrayList<>();
        } else {
            recipeIngredients.clear();
        }

        if (form.getIngredientsInp() != null)
            for (RecipeIngredientFormBean ingredientFormBean : form.getIngredientsInp()) {
                Ingredient ingredient = ingredientDAO.findById(ingredientFormBean.getId());


                RecipeIngredient recipeIngredient = null;
                if (recipe.getId() != null) {
                    recipeIngredient = recipeIngredientDAO.getRecipeIngredientByRecipeAndIngredient(recipe, ingredient);
                }
                if (recipeIngredient == null)
                    recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setMeasure(ingredientFormBean.getMeasure());
                recipeIngredients.add(recipeIngredient);
                if (recipeIngredient.getId() == null && recipe.getId() != null)
                    recipeIngredientDAO.save(recipeIngredient);
            }

        recipe.setRecipeIngredients(recipeIngredients);
        recipe = recipeDAO.save(recipe);
        log.info("Recipe submitted successfully. Recipe ID: {}", recipe.getId());

        return recipe;
    }

}
