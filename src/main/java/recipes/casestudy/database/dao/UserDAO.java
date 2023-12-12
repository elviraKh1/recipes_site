package recipes.casestudy.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.casestudy.database.entity.User;

public interface UserDAO extends JpaRepository<User, Long> {

    public User findByEmailIgnoreCase(String email);

}