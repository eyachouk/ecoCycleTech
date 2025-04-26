package tn.esprit.ecocycletech.Entity.UserManagement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserAndToken {
    private final User user;
    private final String token;
}
