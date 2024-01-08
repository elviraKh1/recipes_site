package recipes.casestudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.casestudy.database.dao.UserDAO;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.formbean.RegisterUserFormBean;

import java.util.Date;


@Slf4j
@Service
public class UserService {
    // 1) Alter the user table add a column called create_date of time timestamp
    // 2) update the user entity and add teh create_date field
    // 3) Google how to use a @Temporal to define the create_date field as a timestamp
    // 4) In the createNewUser function set the create_date field to the current date and time before saving to the database
    @Autowired
    private UserDAO userDAO;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(RegisterUserFormBean form) {

        User user = new User();

        user.setEmail(form.getEmail().toLowerCase());
        user.setCreateDate(new Date());

        String encoded = passwordEncoder.encode(form.getPassword());
        log.debug("Encoded password: " + encoded);
        user.setPassword(encoded);

        return userDAO.save(user);
    }

}
