package tn.esprit.ecocycletech.Controller.UserManagement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.DTO.ChangePassword;
import tn.esprit.ecocycletech.DTO.MailBody;

import tn.esprit.ecocycletech.Entity.UserManagement.ForgotPassword;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Repository.UserManagement.IForgotPasswordRepository;
import tn.esprit.ecocycletech.Repository.UserManagement.IUserRepository;
import tn.esprit.ecocycletech.Service.UserManagement.ForgotPasswordServiceImpl;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final IUserRepository userRepository;
    private final ForgotPasswordServiceImpl forgotPasswordService;
    private final IForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        int otp = otpGenerator();

        MailBody mailBody =MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot password request : " + otp)
                .subject("OTP for Forgot Password Request")
                .build();

        ForgotPassword forgotPassword= ForgotPassword.builder()
                .otp(otp)
                .expiryDate(new Date(System.currentTimeMillis()+300000))
                .user(user)
                .build();

        try{
            forgotPasswordService.sendSimpleMessage(mailBody);}
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }

        forgotPasswordRepository.save(forgotPassword);

        return ResponseEntity.ok("verification email sent successfully");

    }
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword forgotPassword=forgotPasswordRepository.findByOtpAndUser(otp,user)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid OTP for email : " + email));

        if (forgotPassword.getExpiryDate().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getIdForgotPassword());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("OTP has expired");
        }

        return ResponseEntity.ok("OTP verified successfully");
    }
    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, @PathVariable String email) {

        if(!Objects.equals(changePassword.password(),changePassword.repeatPassword()))
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Passwords do not match");

        String encodedPassword = passwordEncoder.encode(changePassword.password());

        userRepository.updatePassword(email,encodedPassword);

        return ResponseEntity.ok("Password changed successfully");
    }
    private Integer otpGenerator()
    {
        Random rand = new Random();

        return rand.nextInt(100_000,999999);
    }
}
