package tn.esprit.ecocycletech.Repository.UserManagement;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByIdUser(int idUser);
    User findByUsername(String username);
    @Transactional
    @Modifying
    @Query("update User u set u.password=?2 where u.email=?1")
    void updatePassword(String password, String email);
    List<User> findAll();
}
