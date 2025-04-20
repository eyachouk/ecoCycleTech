package tn.esprit.ecocycletech.Service.ReclamationsManagement;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReclamationServiceImpl {
}
=======
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Repository.ReclamationsManagement.IReclamationRepository;
import tn.esprit.ecocycletech.Repository.UserManagement.IUserRepository;
import tn.esprit.ecocycletech.Service.UserManagement.UserServiceImpl;

@Service
public class ReclamationServiceImpl  implements IReclamationService {
    @Autowired
    private IUserRepository userRepository;
    private  IReclamationRepository reclamationRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @SuppressWarnings("unused")
    private static final List<String> URGENT_KEYWORDS = Arrays.asList(
        "refund not processed", "lost package", "scam"
    );


    public ReclamationServiceImpl(IReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }


    @Override
public Reclamation addReclamation(Reclamation reclamation) {
    User user = reclamation.getUser();

    if (user != null && user.getIdUser() != null) {
        if (!userRepository.existsById(user.getIdUser())) {
            userServiceImpl.createUser(user); 
        } else {
            user = userRepository.findById(user.getIdUser()).get();
            reclamation.setUser(user);
        }
    }

    
    
        reclamation.setEtatReclamation(Etat.NONAFFECTEE); 
    

    reclamation.setDateReclamation(new Date());

    return reclamationRepository.save(reclamation);
}

   /* @Override
    public Reclamation updateReclamation(Reclamation reclamation) {
        return  this.reclamationRepository.save(reclamation);
    }*/
    @Override
    public Reclamation updateReclamation(Reclamation reclamation) {
        Optional<Reclamation> existingReclamationOpt = reclamationRepository.findById(reclamation.getIdReclamation());
    
        if (existingReclamationOpt.isPresent()) {
            Reclamation existingReclamation = existingReclamationOpt.get();
    
            existingReclamation.setTitreReclamation(reclamation.getTitreReclamation());
            existingReclamation.setDescriptionReclamation(reclamation.getDescriptionReclamation());
            existingReclamation.setEtatReclamation(reclamation.getEtatReclamation());
    
            User updatedUser = reclamation.getUser();
            if (updatedUser != null && updatedUser.getIdUser() != null) {
                Optional<User> existingUser = userRepository.findById(updatedUser.getIdUser());
                existingUser.ifPresent(existingReclamation::setUser);
            }
    
            return reclamationRepository.save(existingReclamation);
        } else {
            throw new RuntimeException("Reclamation with ID " + reclamation.getIdReclamation() + " not found.");
        }
    }

    @Override
    public void deleteReclamation(int idReclamation) {
        reclamationRepository.deleteById(idReclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return this.reclamationRepository.findAll();
    }

    @Override
    public Reclamation getReclamationById(int idReclamation) {
        return reclamationRepository.findByIdReclamation(idReclamation);

        //return this.reclamationRepository.findByIdReclamation(idReclamation);
    }

    @Override
    public Optional <Reclamation> getReclamationsByUserId(int userId) {
        return reclamationRepository.findByUserIdUser(userId) ;
    }

    @Override
    public List<Reclamation> getReclamationsByEtat(Etat etat) {
        return reclamationRepository.findByEtatReclamation(etat);
    }

    @Override
    public List<Reclamation> searchReclamationsByTitre(String keyword) {
        return reclamationRepository.findByTitreReclamationContainingIgnoreCase(keyword);
    }

    @Override
    public List<Reclamation> getReclamationsByDate(Date date) {
        return reclamationRepository.findByDateReclamation(date);
    }

/* 
    private boolean containsUrgentKeyword(String text) {
        if (text == null) return false;
        text = text.toLowerCase();
    
       
        String[] urgentPatterns = {
            "refund\\s*not\\s*processed", 
            "lost\\s*package",
            "scam",
            "fraud",
            "urgent",
            "delayed\\s*delivery", // "delayed delivery"
            "unsolved\\s*claim" // "unsolved claim"
        };
    
        // Vérifie si un des modèles de mots-clés correspond au texte
        for (String pattern : urgentPatterns) {
            if (text.matches(".*" + pattern + ".*")) {
                return true;
            }
        } 
        return false;
    } */
    

    @Override
    public boolean existsByTitreReclamation(String titreReclamation) {
        // Vérifier si le titre existe dans la base de données
        return reclamationRepository.existsByTitreReclamation(titreReclamation);
    }
   }
>>>>>>> e88a1f3 (update)
