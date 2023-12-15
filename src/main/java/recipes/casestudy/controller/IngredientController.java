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
import recipes.casestudy.database.dao.IngredientDAO;
import recipes.casestudy.database.entity.Ingredient;
import recipes.casestudy.formbean.IngredientFormBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;
import recipes.casestudy.service.IngredientService;

@Slf4j
@Controller
public class IngredientController {

    @Autowired
    private IngredientDAO ingredientDAO;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("/ingredient/add")
    public ModelAndView createIngredient() {
        ModelAndView response = new ModelAndView("ingredient/edit");
        log.info("In create ingredient with NO args");
        return response;
    }

    @GetMapping("/ingredient/edit/{id}")
    public ModelAndView editIngredient(@PathVariable int id, @RequestParam(required = false) String success) {
        log.info("######################### In edit  ingredient with id " + id + " #########################");
        ModelAndView response = new ModelAndView("ingredient/edit");
        Ingredient ingredient = ingredientDAO.findById(id);

        if (!StringUtils.isEmpty(success)) {
            response.addObject("success", success);
        }

        IngredientFormBean form = new IngredientFormBean();
        if (ingredient != null) {
            form.setName(ingredient.getName());
            form.setId(ingredient.getId());
        } else {
            log.warn("?????????? ingredient with id " + id + " NOT found ??????????");
        }
        response.addObject("form", form);

        return response;
    }

    @GetMapping("/ingredient/submit")
    public ModelAndView submitIngredient(@Valid IngredientFormBean form, BindingResult bindingResult) {
        ModelAndView response = new ModelAndView("ingredient/edit");
        log.info("######################### In submit ingredient with args #########################");

        if (bindingResult.hasErrors()) {
            log.info("######################### In create ingredient submit -HAS ERRORS #########################");
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error " + error.getDefaultMessage());
            }
            response.addObject("form", form);
            response.addObject("errors", bindingResult);
            return response;
        }

        Ingredient ingredient = ingredientService.addIngredient(form);
        response.setViewName("redirect:/ingredient/edit/" + ingredient.getId() + "?success=Ingredient Saved Successfully");

        return response;
    }

    @GetMapping("/ingredient/show/")
    public ModelAndView showIngredient(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("ingredient/show");
        log.info("######################### In /ingredient /show with id " + id + " #########################");

        Ingredient ingredient = ingredientDAO.findById(id);
        if (id == null) {
            log.info("ingredient with id " + id + " not found");
            response.setViewName("redirect:/error/404");
            return response;
        }

        response.addObject("ingredient", ingredient);
        return response;
    }

}
