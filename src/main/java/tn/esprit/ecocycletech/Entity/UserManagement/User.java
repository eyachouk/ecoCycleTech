    package tn.esprit.ecocycletech.Entity.UserManagement;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.Pattern;
    import lombok.*;
    import org.springframework.format.annotation.DateTimeFormat;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import tn.esprit.ecocycletech.Entity.AppareilsManagement.Avis;
    import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;
    import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;
    import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
    import tn.esprit.ecocycletech.Entity.Enumerations.UserRole;
    import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;
    import tn.esprit.ecocycletech.Entity.EvenementsManagement.TicketEvenement;
    import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;
    import jakarta.persistence.*;
    import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;

    import java.io.Serializable;
    import java.time.LocalDate;
    import java.util.Collection;
    import java.util.Date;
    import java.util.List;
    //@Data
    @Entity
    @Builder
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor

    public class User implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        //@Column(unique = true, nullable = false)
        private int idUser;
        @Column(nullable = false)
        private String nom;
        @Column(nullable = false)
        private String prenom;
        @Column(nullable = false)
        @Email(message = "Email Format Invalid")
        @Pattern(regexp = ".+@.+\\..+", message = "Must be a valid email (ex: user@example.com)")
        private String email;
        @Column(nullable = false)
        private String username;
        @Column(nullable = false)
        private Long numTelephone;
        @Temporal(TemporalType.DATE)
        @Column(nullable = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate dateNaissance;
        private String adresse;
        @Enumerated(EnumType.STRING)
        @Builder.Default
        private UserRole role=UserRole.USER;
        //@Column(nullable = true)
        private String photoDeProfil;
        @Column(nullable = false)

        private String password;

        @Builder.Default
        private boolean isActive = true;
        @Builder.Default
        private boolean isBanned = false;



        //foreign keys
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Reclamation> reclamationList;
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<CommandeReparation> commandeReparationList;
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<TicketEvenement> ticketEvenementList;
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<DemandeRecyclage> demandeRecyclageList;
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
        private List<Avis> avisList;
        @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
        private List<Reservation> reservationList;
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "idEspace", referencedColumnName = "idEspace")
        private EspaceStockage espace;
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(role.name()));
        }
        @OneToOne
        private ForgotPassword forgotPassword;
    }
