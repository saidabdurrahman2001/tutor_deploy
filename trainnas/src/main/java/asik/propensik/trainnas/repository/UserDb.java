package asik.propensik.trainnas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asik.propensik.trainnas.model.User;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional

public interface UserDb extends JpaRepository<User, Long> {
    List<User> findByNameContainingIgnoreCase(String name);
    
    List<User> findByRole(String role);
}
