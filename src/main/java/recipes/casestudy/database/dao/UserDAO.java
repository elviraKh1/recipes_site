package recipes.casestudy.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import recipes.casestudy.database.entity.User;

public interface UserDAO extends JpaRepository<User, Long> {

    User findByEmailIgnoreCase(String email);

    @Transactional
    @Modifying
    int deleteUserById(Integer id);
}