package tn.esprit.ecocycletech.Repository.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
