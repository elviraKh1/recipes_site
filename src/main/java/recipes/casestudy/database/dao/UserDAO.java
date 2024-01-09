package recipes.casestudy.database.dao;

import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import recipes.casestudy.database.entity.User;
import recipes.casestudy.database.entity.UserRole;

public interface UserDAO extends JpaRepository<User, Long> {

    User findByEmailIgnoreCase(String email);

    @Transactional
    @Modifying
    int deleteUserById(Integer id);
}