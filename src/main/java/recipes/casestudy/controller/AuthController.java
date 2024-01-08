package recipes.casestudy.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import recipes.casestudy.database.dao.UserDAO;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.formbean.RegisterUserFormBean;
import recipes.casestudy.sequirity.AuthenticatedUserService;
import recipes.casestudy.service.UserService;

@Slf4j
@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("/auth/register")
    public ModelAndView register() {
        ModelAndView response = new ModelAndView();
        response.setViewName("auth/register");

        return response;
    }

    @GetMapping("/auth/login")
    public ModelAndView login() {
        ModelAndView response = new ModelAndView();
        response.setViewName("auth/login");
        return response;
    }

    @GetMapping("/auth/registerSubmit")
    public ModelAndView register(@Valid RegisterUserFormBean form, BindingResult bindingResult, HttpSession session) {
        log.info("######################### In register user #########################");

        User user = userDAO.findByEmailIgnoreCase(form.getEmail());

        if (user != null) {
            bindingResult.rejectValue("email", "field.error", "User with this email exist");
        }

        if (bindingResult.hasErrors()) {
            log.info("######################### In  register user submit -HAS ERRORS #########################");
            ModelAndView response = new ModelAndView("auth/register");
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error " + error.getDefaultMessage());
            }
            response.addObject("form", form);
            response.addObject("errors", bindingResult);

            return response;
        }

        user = userService.createNewUser(form);

        authenticatedUserService.authenticateNewUser(session, user.getEmail(), form.getPassword());
        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/");

        return response;
    }
}

