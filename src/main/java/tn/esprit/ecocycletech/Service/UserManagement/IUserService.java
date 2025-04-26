package tn.esprit.ecocycletech.Service.UserManagement;

import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User getUserById(int id);
    User createUser(User user);
    User updateUser(int id, User user);
    void deleteUser(int id);
    User getUserByEmail(String email);
}
