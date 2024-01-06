package recipes.casestudy.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import recipes.casestudy.database.dao.IngredientDAO;
import recipes.casestudy.database.entity.Ingredient;
import recipes.casestudy.formbean.IngredientFormBean;
import recipes.casestudy.formbean.LabelValueAutocmpleteBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;
import recipes.casestudy.service.IngredientService;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/ingredient/detail/")
    public ModelAndView showIngredient(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("ingredient/detail");
        log.info("######################### In /ingredient /detail with id " + id + " #########################");

        Ingredient ingredient = ingredientDAO.findById(id);
        if (id == null) {
            log.info("ingredient with id " + id + " not found");
            response.setViewName("redirect:/error/404");
            return response;
        }

        response.addObject("ingredient", ingredient);
        return response;
    }

//    @GetMapping("/ingredient/search")
//    public ModelAndView searchIngredient(@RequestParam(required = false) String search) {
//        ModelAndView response = new ModelAndView("ingredient/all");
//        log.debug("######################### Search ingredient with " + search + " #########################");
//        List<Ingredient> ingredients;
//        if (!StringUtils.isEmpty(search)) {
//            search = "%" + search + "%";
//            ingredients = ingredientDAO.findByName(search);
//            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ findByText " + ingredients.toString());
//        } else {
//            ingredients = ingredientDAO.findAll();
//            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ findAll" + ingredients.toString());
//        }
//
//        response.addObject("ingredients", ingredients);
//        return response;
//    }

    @GetMapping("/ingredient/search")
    public ModelAndView searchIngredient(@RequestParam(defaultValue = "0", required = false) Integer page,
                                       @RequestParam(defaultValue = "2", required = false) Integer size
    ) {
        ModelAndView response = new ModelAndView("/ingredient/search");
        log.debug("######################### All ingredients with " + " #########################");
        Pageable paging = PageRequest.of(page, size);
        Page<Ingredient> ingredients = ingredientDAO.findAll(paging);

        response.addObject("ingredients", ingredients);
        return response;
    }

    @RequestMapping(value={"/recipe/ingredientAutocomplete","/recipe/edit/ingredientAutocomplete"})
    @ResponseBody
    public List<LabelValueAutocmpleteBean> ingredientAutocomplete(@RequestParam(value="term", required = false, defaultValue="") String term) {
        List<LabelValueAutocmpleteBean> suggestions = new ArrayList<>();
        log.debug("######################### All ingredients with " + term+ " #########################");
        List<Ingredient>  ingredients=new ArrayList<>();

        if (!StringUtils.isEmpty(term)) {
            term = "%" + term + "%";
            ingredients = ingredientDAO.findByNameLikeIgnoreCase(term);
            log.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++ find " + ingredients.size()+" ingredients");
        }
        for (Ingredient ingredient : ingredients) {
                    LabelValueAutocmpleteBean lv= new LabelValueAutocmpleteBean(ingredient.getId(),ingredient.getName());
                    suggestions.add(lv);
            }
        return suggestions;
    }
//    @RequestMapping(value="/plantNamesAutocomplete")
//    @ResponseBody
//    public List<LabelValueDTO> plantNamesAutocomplete(@RequestParam(value="term", required = false, defaultValue="") String term)  {
//        List<LabelValueDTO> suggestions = new ArrayList<LabelValueDTO>();
//        try {
//            // only update when term is three characters.
//            if (term.length() == 3) {
//                firstThreeCharacters = term;
//                allPlants = specimenService.fetchPlants(term);
//            }
//
//            for (PlantDTO plantDTO : allPlants) {
//                if (plantDTO.toString().contains(term)) {
//                    LabelValueDTO lv = new LabelValueDTO();
//                    lv.setLabel(plantDTO.toString());
//                    lv.setValue(Integer.toString(plantDTO.getGuid()));
//                    suggestions.add(lv);
//                }
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            log.error("Exception in autocomplete", e);
//        }
//
//        return suggestions;
//
//    }

}
