package tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CommentaireDemande;
import tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement.ICommentaireDemandeRepository;
import tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement.IDemandeRecyclageRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentaireDemandeServiceImpl implements ICommentaireDemandeService {

        @Autowired
        ICommentaireDemandeRepository commentaireRepository;
        @Autowired
        IDemandeRecyclageRepository demandeRepo;

        @Override
        public CommentaireDemande addComment(CommentaireDemande commentaireDemande) {
            commentaireDemande.setDateCreation(new Date());

            if (commentaireDemande.getDemandeRecyclage() != null &&
                    commentaireDemande.getDemandeRecyclage().getIdDemandeRecyclage() != 0) {

                commentaireDemande.setDemandeRecyclage(
                        demandeRepo.findById(commentaireDemande.getDemandeRecyclage().getIdDemandeRecyclage())
                                .orElse(null)
                );

            }
            System.out.println("ID reçu: " + commentaireDemande.getDemandeRecyclage().getIdDemandeRecyclage());
            return commentaireRepository.save(commentaireDemande);
        }



        @Override
        public void deleteComment(int idCommentaire) {
            commentaireRepository.deleteById(idCommentaire);
        }



        @Override
        public List<CommentaireDemande> getCommentsByDemandeId(int idDemande) {
            return commentaireRepository.findByDemandeRecyclageIdDemandeRecyclage(idDemande);
        }
}
