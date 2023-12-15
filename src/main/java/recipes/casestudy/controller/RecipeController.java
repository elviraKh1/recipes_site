package recipes.casestudy.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import recipes.casestudy.database.dao.RecipeDAO;
import recipes.casestudy.database.entity.Recipe;
import recipes.casestudy.formbean.RecipeFormBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;
import recipes.casestudy.service.RecipeService;

@Slf4j
@Controller
public class RecipeController {

    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("/recipe/edit/{id}")
    public ModelAndView editCustomer(@PathVariable int id, @RequestParam(required = false) String success) {
        log.info("######################### In edit  recipe with id " + id + " #########################");
        ModelAndView response = new ModelAndView("recipe/edit");
        Recipe recipe = recipeDAO.findById(id);

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

        return response;
    }

    @GetMapping("/recipe/submit")
    public ModelAndView submitRecipe(@Valid RecipeFormBean form, BindingResult bindingResult) {
        ModelAndView response = new ModelAndView("recipe/edit");
        log.info("######################### In submit recipe with args #########################");

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

    @GetMapping("/recipe/show/")
    public ModelAndView showRecipe(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("recipe/show");
        log.info("######################### In /recipe /show with id " + id + " #########################");

        Recipe recipe = recipeDAO.findById(id);
        if (id == null) {
            log.info("recipe with id " + id + " not found");
            response.setViewName("redirect:/error/404");
            return response;
        }

        response.addObject("recipe", recipe);
        return response;
    }


}
