package tn.esprit.ecocycletech.Entity.UserManagement;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import tn.esprit.ecocycletech.Repository.UserManagement.IUserRepository;
import tn.esprit.ecocycletech.Repository.UserManagement.IVerificationTokenRepository;
import tn.esprit.ecocycletech.Service.UserManagement.UserServiceImpl;

@Component
@RequiredArgsConstructor
public class VerificationMailListener {

    private final IUserRepository userRepo;
    private final IVerificationTokenRepository tokenRepo;
    private final UserServiceImpl helper;          // just to reuse sendVerificationEmail()

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(VerificationMailEvent ev) {
        User user = userRepo.findById(ev.userId()).orElseThrow();
        String token = tokenRepo.findByUser(user).map(VerificationToken::getToken)
                .orElseThrow();
        helper.sendVerificationEmail(user, token);
    }
}

