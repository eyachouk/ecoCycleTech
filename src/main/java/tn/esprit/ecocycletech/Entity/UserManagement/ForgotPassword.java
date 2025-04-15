package tn.esprit.ecocycletech.Entity.UserManagement;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idForgotPassword;

    private Integer otp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}