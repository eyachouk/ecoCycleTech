package tn.esprit.ecocycletech.Repository.StockageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.StatutEspace;

import java.util.Date;
import java.util.List;

@Repository
public interface IEspaceStockageRepository extends JpaRepository<EspaceStockage,Long> {
    public EspaceStockage findByIdEspace(Long id);
    public List<EspaceStockage> findByPlanStockage(PlanStockage planStockage);

    @Query("SELECT e FROM EspaceStockage e WHERE e.dateExpiration <= :date AND e.statut <> :statut")
    List<EspaceStockage> findByDateExpirationBeforeOrEqualAndStatutNot(@Param("date") Date date, @Param("statut") StatutEspace statut);
}
