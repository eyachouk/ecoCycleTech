package tn.esprit.ecocycletech.Controller.StockageManagement;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.StockageManagement.DTOs.PlanSubscriptionCount;
import tn.esprit.ecocycletech.Entity.StockageManagement.DTOs.SubscriptionCountByDate;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Subscription;
import tn.esprit.ecocycletech.Service.StockageManagement.IEspaceStockageService;
import tn.esprit.ecocycletech.Service.StockageManagement.ISubscriptionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subscription")
@AllArgsConstructor
public class SubscriptionController {

    @Autowired
    ISubscriptionService subscriptionService;

    @GetMapping("/getAllSubscriptions")
    public List<Subscription> retrieveAllSubscriptions() {
        return subscriptionService.GetAllSubscriptions();
    }

    @GetMapping("/getSubscription/{id}")
    public Subscription retrieveSubscription(@PathVariable("id") Long id) {
        return subscriptionService.GetSubscription(id);
    }

    @PostMapping("/addSubscription")
    public Subscription addSubscription(@RequestBody Subscription s) {
        return subscriptionService.addSubscription(s);
    }

    @PutMapping("/updateSubscription/{id}")
    public Subscription updateSubscription(@PathVariable("id") Long id, @RequestBody Subscription s) {
        return subscriptionService.updateSubscription(id, s);
    }

    @DeleteMapping("/deleteSubscription/{id}")
    public void deleteSubscription(@PathVariable("id") Long id) {
        subscriptionService.DeleteSubscription(id);
    }


    @GetMapping("/activeSubscriptionExist/{userId}")
    public ResponseEntity<Boolean> hasActiveSubscription(@PathVariable Long userId) {
        boolean hasSubscription = subscriptionService.hasActiveSubscription(userId);
        return ResponseEntity.ok(hasSubscription);
    }

    @GetMapping("/activeEspace/{userId}")
    public ResponseEntity<EspaceStockage> getActiveEspace(@PathVariable Long userId) {
        Optional<EspaceStockage> espace = subscriptionService.GetActiveEspaceStockageByUserId(userId);
        return espace.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/getSubscriptionByEspace/{espaceId}")
    public Subscription GetSubscriptionByEspace(@PathVariable Long espaceId) {
        return subscriptionService.findByEspace(espaceId);
    }


    @GetMapping("/subscriptionslast7days")
    public List<SubscriptionCountByDate> getLast7DaysSubscriptions() {
        return subscriptionService.getSubscriptionsLast7Days();
    }

    @GetMapping("/bestplans")
    public List<PlanSubscriptionCount> getBestPlans() {
        return subscriptionService.getBestPlansBySubscriptions();
    }

    @GetMapping("/plans-count")
    public List<PlanSubscriptionCount> getPlanSubscriptionCounts() {
        return subscriptionService.getPlanSubscriptionCounts();
    }

}
