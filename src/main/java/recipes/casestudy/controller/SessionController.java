package recipes.casestudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@ControllerAdvice
public class SessionController {

    @ExceptionHandler(SessionAuthenticationException.class)
    public ModelAndView sessionExpired(HttpServletRequest request, Exception ex) {
        ModelAndView response = new ModelAndView("auth/login");

        log.debug("Users session expired ");
        response.setViewName("redirect:/auth/login?m=Session Expired. Please log in again");
        return response;
    }
}
