package recipes.casestudy.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.casestudy.database.entity.UserRole;

import java.util.List;

public interface UserRoleDAO extends JpaRepository<UserRole, Long> {

    public List<UserRole> findByUserId(Integer userId);
}