package tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CommentaireDemande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCommentaire;

    public DemandeRecyclage getDemandeRecyclage() {
        return demandeRecyclage;
    }

    public void setDemandeRecyclage(DemandeRecyclage demandeRecyclage) {
        this.demandeRecyclage = demandeRecyclage;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Column(columnDefinition = "TEXT")
    private String content;

    public int getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) // <-- allow null
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "demande_id")
    @JsonBackReference // 🔄 Corrige la relation bidirectionnelle
    private DemandeRecyclage demandeRecyclage;

}
