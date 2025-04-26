package tn.esprit.ecocycletech.Service.StockageManagement;

import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Subscription;

import java.util.List;
import java.util.Optional;

public interface ISubscriptionService {
    public List<Subscription> GetAllSubscriptions();
    public Subscription GetSubscription(Long id);
    Subscription addSubscription (Subscription e);
    Subscription updateSubscription (long id, Subscription e);
    void DeleteSubscription(Long id);
    boolean hasActiveSubscription(Long userId);

    public Optional<EspaceStockage> GetActiveEspaceStockageByUserId(Long userId);

}
