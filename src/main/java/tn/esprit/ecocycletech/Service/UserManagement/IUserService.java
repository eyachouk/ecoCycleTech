package tn.esprit.ecocycletech.Service.UserManagement;

import tn.esprit.ecocycletech.DTO.*;
import tn.esprit.ecocycletech.Entity.Enumerations.UserStatus;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {
    User registerUser(RegisterRequest request);
     LoginResponse login(LoginRequest request);
    LoginResponse verifyEmail(String token);
    User findOrCreateGoogleUser(String email, String name, boolean emailVerified);
    User findOrCreateFacebookUser(String email, String name, boolean emailVerified);
    public User updateUserProfile(int userId, UserUpdateRequest request);
    User getUserById(int userId);
    User loadUserByUsername(String username);
    List<User> getAllUsers();

    void deleteUser(int id);

    User changeUserStatus(int id, UserStatus userStatus);

    String findVerificationToken(User user);

    //Optional<User> getOptional(Integer id);

    Optional<User> getByEmail(String email);

    Map<String, Integer> calculateUserAgeStatistics();
}
