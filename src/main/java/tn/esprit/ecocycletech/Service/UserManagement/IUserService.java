package tn.esprit.ecocycletech.Service.UserManagement;

import tn.esprit.ecocycletech.DTO.LoginRequest;
import tn.esprit.ecocycletech.DTO.LoginResponse;
import tn.esprit.ecocycletech.DTO.RegisterRequest;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

public interface IUserService {
    User registerUser(RegisterRequest request);
     LoginResponse login(LoginRequest request);

    }
