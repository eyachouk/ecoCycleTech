package tn.esprit.ecocycletech.Repository.StockageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Subscription;

import java.util.Optional;

public interface ISubscriptionRepository extends JpaRepository<Subscription,Long> {
    public Subscription findById(long id);

    @Query("SELECT s FROM Subscription s WHERE s.user.idUser = :userId AND s.espace.statut = 'Active' AND s.endDate > CURRENT_DATE")
    Optional<Subscription[]> findActiveSubscriptionByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Subscription s WHERE s.user.idUser = :userId AND s.espace.statut = 'Active' AND s.endDate > CURRENT_DATE")
    Optional<Subscription> findByUserIdAndIsActiveTrue(Long userId);
}