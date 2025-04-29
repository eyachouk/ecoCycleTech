package tn.esprit.ecocycletech.Repository.StockageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Subscription;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ISubscriptionRepository extends JpaRepository<Subscription,Long> {
    public Subscription findById(long id);

    @Query("SELECT s FROM Subscription s WHERE s.user.idUser = :userId AND s.espace.statut = 'Active' AND s.endDate > CURRENT_DATE")
    Optional<Subscription[]> findActiveSubscriptionByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM Subscription s WHERE s.user.idUser = :userId AND s.espace.statut = 'Active' AND s.endDate > CURRENT_DATE")
    Optional<Subscription> findByUserIdAndIsActiveTrue(Long userId);


    Subscription findSubscriptionByEspace(EspaceStockage espace);



    @Query("SELECT FUNCTION('DATE', s.paidAt), COUNT(s) " +
            "FROM Subscription s " +
            "WHERE s.paidAt BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE', s.paidAt) " +
            "ORDER BY FUNCTION('DATE', s.paidAt)")
    List<Object[]> countSubscriptionsByPaidAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT s.planStockage.titre, COUNT(s) " +
            "FROM Subscription s " +
            "GROUP BY s.planStockage.titre")
    List<Object[]> countSubscriptionsByPlanStockage();


    @Query("SELECT s.planStockage.titre, COUNT(s) " +
            "FROM Subscription s " +
            "GROUP BY s.planStockage.titre " +
            "ORDER BY COUNT(s) DESC")
    List<Object[]> findPlanSubscriptionCounts();

}