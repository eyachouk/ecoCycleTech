package tn.esprit.ecocycletech.Service.UserManagement;

import lombok.AllArgsConstructor;
<<<<<<< HEAD
import org.springframework.stereotype.Service;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Repository.UserManagement.IUserRepository;
import java.util.List;
import java.util.Optional;
>>>>>>> e88a1f3 (update)

@Service
@AllArgsConstructor
public class UserServiceImpl {
<<<<<<< HEAD
}
=======

    @Autowired
    private IUserRepository userRepository;

    // Create: Save a new user
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }

    // Read: Find a user by ID
    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow();
    }

    // Read: Find all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update: Update an existing user
    public User updateUser(int id, User updatedUser) {
        if (updatedUser == null) {
            throw new IllegalArgumentException("Updated user cannot be null");
        }
        User existingUser = getUserById(id); // Ensure user exists
        // Update fields (example: assuming User has username and email)
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        // Add other fields as needed
        return userRepository.save(existingUser);
    }

    // Delete: Delete a user by ID
    public void deleteUser(int id) {
      
        userRepository.deleteById(id);
    }
}
>>>>>>> e88a1f3 (update)
