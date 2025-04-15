package tn.esprit.ecocycletech.Repository.UserManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.ecocycletech.Entity.UserManagement.*;

import java.util.Optional;

public interface IForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
    @Query("select f from ForgotPassword f where f.otp= ?1 and f.user=?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);
    //Optional<ForgotPassword> findTopByUserOrderByExpiryDateDesc(User user);
    void deleteByUser(User user);
}
