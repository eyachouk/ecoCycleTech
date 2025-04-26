package tn.esprit.ecocycletech.Repository.StockageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;

import java.util.Optional;

@Repository
public interface IFichierRepository extends JpaRepository<Fichier,Long> {

    public Fichier findByIdFichier(Long id);

    public Fichier findByCloudinaryPublicId(String publicId);
}
