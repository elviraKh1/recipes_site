package recipes.casestudy.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import recipes.casestudy.formbean.RecipeIngredientFormBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;
import recipes.casestudy.service.RecipeService;

import java.util.ArrayList;
import java.util.List;

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
        List<RecipeIngredientFormBean> ingredientFormBeans = new ArrayList<>();
        if (recipe != null) {
            form.setName(recipe.getName());
            form.setCategory(recipe.getCategory());
            form.setInstructions(recipe.getInstructions());
            form.setImageUrl(recipe.getImageUrl());
            form.setId(recipe.getId());


            for (RecipeIngredient recipeIngredient : ingredients) {
                RecipeIngredientFormBean ingredientFormBean = new RecipeIngredientFormBean();
                ingredientFormBean.setId(recipeIngredient.getIngredient().getId());
                ingredientFormBean.setName(recipeIngredient.getIngredient().getName());
                ingredientFormBean.setMeasure(recipeIngredient.getMeasure());
                ingredientFormBeans.add(ingredientFormBean);
            }

        } else {
            log.warn("?????????? recipe with id " + id + " NOT found ??????????");
        }
        form.setIngredientsInp(ingredientFormBeans);
        response.addObject("form", form);

        return response;
    }

    @PostMapping("/recipe/submit")
    public ModelAndView submitRecipe(//@RequestParam("image_url") MultipartFile imageFile,
                                     @Valid RecipeFormBean form,
                                     BindingResult bindingResult) {
        ModelAndView response = new ModelAndView("recipe/edit");

        log.debug("######################### In submit recipe with args #########################");
//        log.debug(" In fileupload name " + imageFile.getName());
//        log.debug(" In fileupload size " + imageFile.getSize());

        List<RecipeIngredientFormBean> ingredientFormBeans = form.getIngredientsInp();
        if (ingredientFormBeans != null) {
            ingredientFormBeans.removeIf(ingredientFormBean -> ingredientFormBean.getId() == null);
            for (RecipeIngredientFormBean ingredientFormBean : ingredientFormBeans) {
                Ingredient ingredient = ingredientDAO.findById(ingredientFormBean.getId());
                if (ingredient == null) {
                    bindingResult.rejectValue("ingredientsInp", "field.error", "Field cannot be found in database");
                    break;
                }
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


        Recipe recipe = recipeService.addRecipe(form);

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

    @Transactional
    @GetMapping("/recipe/delete/")
    public ModelAndView deleteRecipe(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("index");
        log.info("######################### In /recipe / delete with id " + id + " #########################");

        User user = authenticatedUserService.loadCurrentUser();
        recipeService.deleteById(id);

        response.addObject("user", user);
        response.setViewName("redirect:/?success=Recipe delete Successfully");
        return response;
    }

    @GetMapping("/recipe/search")
    public ModelAndView searchRecipe(@RequestParam(required = false) String search,
                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "6", required = false) Integer size) {
        ModelAndView response = new ModelAndView("index");
        log.debug("######################### Search recipe with " + search + " #########################");
        Page<Recipe> recipes;
        Pageable paging = PageRequest.of(page, size);

        if (!StringUtils.isEmpty(search)) {
            search = "%" + search.toLowerCase() + "%";
            recipes = recipeDAO.findByNameOrInstructions(search, paging);
            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ findByText " + recipes.toString());
        } else {
            recipes = recipeDAO.findAll(paging);
            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ findAll" + recipes.toString());
        }

        User user = authenticatedUserService.loadCurrentUser();
        response.addObject("user", user);

        response.addObject("recipes", recipes);
        return response;
    }


    @GetMapping("/recipe/category")
    public ModelAndView searchRecipeByCategory(@RequestParam(required = false) String c,
                                               @RequestParam(defaultValue = "0", required = false) Integer page,
                                               @RequestParam(defaultValue = "6", required = false) Integer size) {
        ModelAndView response = new ModelAndView("index");
        log.debug("######################### Search recipe with category" + c + " #########################");
        Page<Recipe> recipes;
        Pageable paging = PageRequest.of(page, size);
        String category = c;
        if (!StringUtils.isEmpty(c)) {
            recipes = recipeDAO.findByCategoryIgnoreCase(c, paging);
            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ findByText " + recipes.toString());
        } else {
            recipes = recipeDAO.findAll(paging);
            category = "";
            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ findAll" + recipes.toString());
        }

        User user = authenticatedUserService.loadCurrentUser();
        response.addObject("user", user);
        response.addObject("category", category);
        response.addObject("recipes", recipes);
        return response;
    }


    @GetMapping("/")
    public ModelAndView allRecipes(@RequestParam(defaultValue = "0", required = false) Integer page,
                                   @RequestParam(defaultValue = "6", required = false) Integer size
    ) {
        ModelAndView response = new ModelAndView("index");
        log.debug("######################### All recipe with " + " #########################");
        Pageable paging = PageRequest.of(page, size);
        Page<Recipe> recipes = recipeDAO.findAll(paging);

        User user = authenticatedUserService.loadCurrentUser();
        response.addObject("user", user);

        response.addObject("recipes", recipes);
        return response;
    }


}
