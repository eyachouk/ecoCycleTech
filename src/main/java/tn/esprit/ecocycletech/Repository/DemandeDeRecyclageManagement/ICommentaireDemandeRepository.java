package tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CommentaireDemande;

import java.util.List;

public interface ICommentaireDemandeRepository extends JpaRepository<CommentaireDemande, Integer> {
    List<CommentaireDemande> findByDemandeRecyclageIdDemandeRecyclage(int idDemandeRecyclage);
}

