package tn.esprit.ecocycletech.Repository.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ecocycletech.Entity.UserManagement.VerificationToken;

import java.util.Optional;

public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByUser_IdUser(Long userId);
}
