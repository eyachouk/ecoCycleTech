package tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement;

import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CommentaireDemande;

import java.util.List;

public interface ICommentaireDemandeService {
    CommentaireDemande addComment(CommentaireDemande commentaireDemande);

    void deleteComment(int idCommentaire);

    List<CommentaireDemande> getCommentsByDemandeId(int idDemande);
}

