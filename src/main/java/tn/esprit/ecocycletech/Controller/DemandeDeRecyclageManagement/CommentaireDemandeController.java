package tn.esprit.ecocycletech.Controller.DemandeDeRecyclageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CommentaireDemande;
import tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement.ICommentaireDemandeService;
import tn.esprit.ecocycletech.Service.Notifications.SmartBadWordFilterService;

import java.util.List;

@RestController
@RequestMapping("/commentaireDemande")
@AllArgsConstructor
public class CommentaireDemandeController {
    @Autowired
    ICommentaireDemandeService commentaireService;
    @Autowired
    SmartBadWordFilterService badWordFilter;
    @PostMapping("/add")
    public CommentaireDemande addComment(@RequestBody CommentaireDemande commentaire) {
        String clean = badWordFilter.sanitize(
                commentaire.getContent()
        );
        commentaire.setContent(clean);
        return commentaireService.addComment(commentaire);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable int id) {
        commentaireService.deleteComment(id);
    }


    @GetMapping("/demande/{id}")
    public List<CommentaireDemande> getCommentsByDemande(@PathVariable int id) {
        return commentaireService.getCommentsByDemandeId(id);
    }
}
