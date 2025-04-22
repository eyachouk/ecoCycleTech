package tn.esprit.ecocycletech.Service.StockageManagement;

import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Subscription;

import java.util.List;

public interface ISubscriptionService {
    public List<Subscription> GetAllSubscriptions();
    public Subscription GetSubscription(Long id);
    Subscription addSubscription (Subscription e);
    Subscription updateSubscription (long id, Subscription e);
    void DeleteSubscription(Long id);
}
