package recipes.casestudy.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import recipes.casestudy.database.dao.IngredientDAO;
import recipes.casestudy.database.dao.RecipeDAO;
import recipes.casestudy.database.dao.RecipeIngredientDAO;
import recipes.casestudy.database.entity.Ingredient;
import recipes.casestudy.database.entity.Recipe;
import recipes.casestudy.database.entity.RecipeIngredient;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.formbean.RecipeFormBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;
import recipes.casestudy.service.RecipeService;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class RecipeController {

    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private IngredientDAO ingredientDAO;

    @Autowired
    private RecipeIngredientDAO recipeIngredientDAO;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("/recipe/add")
    public ModelAndView createRecipe() {
        ModelAndView response = new ModelAndView("recipe/edit");
        log.info("In create recipe with NO args");
        return response;
    }

    @GetMapping("/recipe/edit/{id}")
    public ModelAndView editRecipe(@PathVariable int id, @RequestParam(required = false) String success) {
        log.info("######################### In edit  recipe with id " + id + " #########################");
        ModelAndView response = new ModelAndView("recipe/edit");
        Recipe recipe = recipeDAO.findById(id);
        List<RecipeIngredient> ingredients = recipeIngredientDAO.getRecipeIngredientByRecipe(recipe);
        if (!StringUtils.isEmpty(success)) {
            response.addObject("success", success);
        }

        RecipeFormBean form = new RecipeFormBean();
        if (recipe != null) {
            form.setName(recipe.getName());
            form.setCategory(recipe.getCategory());
            form.setInstructions(recipe.getInstructions());
            form.setImageUrl(recipe.getImageUrl());
            form.setId(recipe.getId());
        } else {
            log.warn("?????????? recipe with id " + id + " NOT found ??????????");
        }
        response.addObject("form", form);
        response.addObject("ingredients", ingredients);
        return response;
    }

    @PostMapping("/recipe/submit")
    public ModelAndView submitRecipe(//@Valid
                                     RecipeFormBean form,
                                     BindingResult bindingResult,
                                     @RequestParam Map<String, String> formData) {
        ModelAndView response = new ModelAndView("recipe/edit");
        ////////////
        Recipe recipe = recipeService.addRecipe(form);

        log.info("######################### In submit recipe with args #########################");
        log.info("######################### formData "+formData);

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String fieldName = entry.getKey();
            if (fieldName.startsWith("ingredientName_")) {
                int cnt = Integer.parseInt(fieldName.substring(fieldName.indexOf("_") + 1));
                log.info("######################### cnt "+cnt);
                log.info("######################### formData.get(\"ingredientId_0\" ) "+formData.get("ingredientId_" + cnt));
                log.info("######################### cnt "+cnt);
                Ingredient ingredient = ingredientDAO.findById(Integer.valueOf(formData.get("ingredientId_" + cnt)));
                //if (!ingredient.getName().equalsIgnoreCase(fieldName)){
                //    bindingResult.rejectValue(fieldName, "field.error", "Field cannot be found in database");
                // }else{
//                List<RecipeIngredient> ingredients =
                log.debug("+++++++++++++++++++++++ ingredient= "+ingredient);
                log.debug("+++++++++++++++++++++++ recipe "+recipe);
                RecipeIngredient recipeIngredient = recipeIngredientDAO.getRecipeIngredientByRecipeAndIngredient(recipe, ingredient);
                log.debug("+++++++++++++++++++++++"+recipeIngredient);
                if (recipeIngredient == null)
                    recipeIngredient = new RecipeIngredient();
                log.debug("+++++++++++++++++++++++"+recipeIngredient.toString());
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setMeasure(
//                            formData.get(                            "quantity_"+cnt)+" "+
                        formData.get("measure_" + cnt));
                recipeIngredientDAO.save(recipeIngredient);
                // };


            }
        }
        if (bindingResult.hasErrors()) {
            log.info("######################### In create recipe  submit -HAS ERRORS #########################");
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error " + error.getDefaultMessage());
            }
            response.addObject("form", form);
            response.addObject("errors", bindingResult);
            return response;
        }

//        Recipe recipe = recipeService.addRecipe(form);

        response.setViewName("redirect:/recipe/edit/" + recipe.getId() + "?success=Recipe Saved Successfully");

        return response;
    }

    @GetMapping("/recipe/detail/")
    public ModelAndView showRecipe(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("recipe/detail");
        log.info("######################### In /recipe /show with id " + id + " #########################");

        Recipe recipe = recipeDAO.findById(id);

        List<RecipeIngredient> ingredients = recipeIngredientDAO.getRecipeIngredientByRecipe(recipe);
        if (id == null) {
            log.info("recipe with id " + id + " not found");
            response.setViewName("redirect:/error/404");
            return response;
        }
        User user = authenticatedUserService.loadCurrentUser();
        response.addObject("ingredients", ingredients);
        response.addObject("recipe", recipe);
        response.addObject("user", user);
        return response;
    }

    @GetMapping("/recipe/search")
    public ModelAndView searchRecipe(@RequestParam(required = false) String search,
                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "5", required = false) Integer size) {
        ModelAndView response = new ModelAndView("index");
        log.debug("######################### Search recipe with " + search + " #########################");
        Page<Recipe> recipes;
        Pageable paging = PageRequest.of(page, size);

        if (!StringUtils.isEmpty(search)) {
            search = "%" + search + "%";
            recipes = recipeDAO.findByNameOrInstructions(search, paging);
            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ findByText " + recipes.toString());
        } else {
            recipes = recipeDAO.findAll(paging);
            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ findAll" + recipes.toString());
        }

        response.addObject("recipes", recipes);
        return response;
    }

    @GetMapping("/")
    public ModelAndView allRecipes(@RequestParam(defaultValue = "0", required = false) Integer page,
                                   @RequestParam(defaultValue = "2", required = false) Integer size
    ) {
        ModelAndView response = new ModelAndView("index");
        log.debug("######################### All recipe with " + " #########################");
        Pageable paging = PageRequest.of(page, size);
        Page<Recipe> recipes = recipeDAO.findAll(paging);

        response.addObject("recipes", recipes);
        return response;
    }


}
