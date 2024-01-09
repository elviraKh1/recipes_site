package recipes.casestudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.casestudy.database.dao.UserDAO;
import recipes.casestudy.database.dao.UserRoleDAO;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.database.entity.UserRole;
import recipes.casestudy.formbean.RegisterUserFormBean;

import java.util.Date;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(RegisterUserFormBean form) {

        User user = new User();

        user.setEmail(form.getEmail().toLowerCase());
        if (form.getName() == null || form.getName().isEmpty()) {
            user.setName(form.getEmail());
        } else {
            user.setName(form.getName());
        }
        user.setCreateDate(new Date());

        String encoded = passwordEncoder.encode(form.getPassword());
        log.debug("Encoded password: " + encoded);
        user.setPassword(encoded);
        user = userDAO.save(user);

        UserRole userRole = new UserRole();
        userRole.setRoleName("MEMBER");
        userRole.setUserId(user.getId());
        userRoleDAO.save(userRole);

        return user;
    }

    public int deleteUser(Integer id) {
        try {
            userRoleDAO.deleteUserRoleByUserId(id);
            return userDAO.deleteUserById(id);
        } catch (Exception e) {
            log.info("User id=" + id + " isn't deleted. Error: " + e.getMessage());
        }
        return 0;
    }

}
