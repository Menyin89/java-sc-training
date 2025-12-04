package employees;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from User u left join fetch u.authorities where u.username = :username")
    Optional<User> findUserByUsername(String username);
}
