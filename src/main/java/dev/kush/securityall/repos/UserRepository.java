package dev.kush.securityall.repos;

import dev.kush.securityall.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = """
                select u,r from User u join u.role r
                where u.email = :email
            """)
    Optional<User> findUserByEmail(@Param("email") String email);
}