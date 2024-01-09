package recipes.casestudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import recipes.casestudy.database.dao.UserDAO;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.service.UserService;

@Slf4j
@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminContoller {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/index")
    public ModelAndView index(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        ModelAndView response = new ModelAndView("admin/index");
        log.debug("######################### Search all users  " + " #########################");
        Pageable paging = PageRequest.of(page, size);
        Page<User> users = userDAO.findAll(paging);

        response.addObject("users", users);
        return response;
    }

    @GetMapping("/admin/delete/{id}")
    public ModelAndView deleteUser(@PathVariable int id,
                                   @RequestParam(defaultValue = "0", required = false) Integer page,
                                   @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        ModelAndView response = new ModelAndView("admin/index");

        log.debug("######################### delete user  " + " #########################");
        String success = "User is deleted Successfully";
        if (userService.deleteUser(id) != 1) {
            success = "User isn't deleted";
        }
        response.setViewName("redirect:/admin/index?success=" + success);
        return response;
    }
}
