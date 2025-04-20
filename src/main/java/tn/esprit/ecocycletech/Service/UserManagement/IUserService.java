package tn.esprit.ecocycletech.Service.UserManagement;

import tn.esprit.ecocycletech.DTO.LoginRequest;
import tn.esprit.ecocycletech.DTO.LoginResponse;
import tn.esprit.ecocycletech.DTO.MailBody;
import tn.esprit.ecocycletech.DTO.RegisterRequest;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

public interface IUserService {
    User registerUser(RegisterRequest request);
     LoginResponse login(LoginRequest request);
    boolean verifyEmail(String token);

    User findOrCreateGoogleUser(String email, String name, boolean emailVerified);
    User findOrCreateFacebookUser(String email, String name, boolean emailVerified);
}
