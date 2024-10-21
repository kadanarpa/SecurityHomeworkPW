
package co.edu.usco.SecuritySpring.persistence.repository;

import co.edu.usco.SecuritySpring.persistence.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    
    Optional<UserEntity> findUserEntityByUsername(String username);
    
    // Con Query
    @Query(value = "SELECT u FROM UserEntity u WHERE u.username = :username", nativeQuery = false)
    Optional<UserEntity> findUser(@Param("username") String username);
}
