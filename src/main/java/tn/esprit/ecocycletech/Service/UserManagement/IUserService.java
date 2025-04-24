package tn.esprit.ecocycletech.Service.UserManagement;

import tn.esprit.ecocycletech.DTO.*;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User registerUser(RegisterRequest request);
     LoginResponse login(LoginRequest request);
    boolean verifyEmail(String token);
    User findOrCreateGoogleUser(String email, String name, boolean emailVerified);
    User findOrCreateFacebookUser(String email, String name, boolean emailVerified);
    public User updateUserProfile(int userId, UserUpdateRequest request);
    User getUserById(int userId);
    User loadUserByUsername(String username);
    List<User> getAllUsers();
}
