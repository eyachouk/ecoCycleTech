package tn.esprit.ecocycletech.Entity.AppareilsManagement;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Avis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAvis;

    private String contenu;

    @ManyToOne
    private Appareil appareil;
    @ManyToOne
    private User user;
}
