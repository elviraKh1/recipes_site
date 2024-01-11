package recipes.casestudy.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        recipe.setAuthorId(user.getId());

        recipe = saveImage(form.getImageFile(), recipe);

        List<RecipeIngredient> recipeIngredients = processRecipeIngredient(recipe, form);

        recipe.setRecipeIngredients(recipeIngredients);
        recipe = recipeDAO.save(recipe);
        log.info("Recipe submitted successfully. Recipe ID: {}", recipe.getId());

        return recipe;
    }

    private Recipe saveImage(MultipartFile imageFile, Recipe recipe) {
        if (imageFile.isEmpty())
            return recipe;
        String filename = System.currentTimeMillis()+imageFile.getOriginalFilename();
        File f = new File("./src/main/webapp/pub/images/" + filename);
        try (OutputStream outputStream = new FileOutputStream(f.getAbsolutePath())) {
            IOUtils.copy(imageFile.getInputStream(), outputStream);
            recipe.setImageUrl("/pub/images/" + filename);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
        return recipe;
    }

    private void deleteImage(Recipe recipe) {
        try {
            File f = new File("./src/main/webapp" + recipe.getImageUrl());
            f.delete();
        } catch (Exception e) {
            log.info("Error during delete file " + recipe);
            log.debug(e.getMessage());
        }
    }


    private List<RecipeIngredient> processRecipeIngredient(Recipe recipe,
                                                           RecipeFormBean form) {
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
        return recipeIngredients;
    }

    public void deleteById(Integer id) {
        Recipe recipe = recipeDAO.findById(id);
        deleteImage(recipe);
        recipeDAO.deleteById(id);
    }

}