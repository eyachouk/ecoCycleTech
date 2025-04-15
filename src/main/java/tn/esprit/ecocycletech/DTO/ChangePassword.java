package tn.esprit.ecocycletech.DTO;

import java.io.Serializable;

public record ChangePassword(String password, String repeatPassword) implements Serializable {
}
