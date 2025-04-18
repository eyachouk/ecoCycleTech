package tn.esprit.ecocycletech.Repository.StockageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;

@Repository
public interface IPlanStockageRepository extends JpaRepository<PlanStockage,Long> {
    public PlanStockage findByIdPlan(long id);
}
