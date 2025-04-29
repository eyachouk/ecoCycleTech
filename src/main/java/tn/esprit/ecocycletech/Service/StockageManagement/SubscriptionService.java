package tn.esprit.ecocycletech.Service.StockageManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.StockageManagement.DTOs.PlanSubscriptionCount;
import tn.esprit.ecocycletech.Entity.StockageManagement.DTOs.SubscriptionCountByDate;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Subscription;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Repository.StockageManagement.IEspaceStockageRepository;
import tn.esprit.ecocycletech.Repository.StockageManagement.IPlanStockageRepository;
import tn.esprit.ecocycletech.Repository.StockageManagement.ISubscriptionRepository;
import tn.esprit.ecocycletech.Repository.UserManagement.IUserRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionService implements ISubscriptionService {

    @Autowired
    private ISubscriptionRepository subscriptionRepo;
    @Autowired
    private IUserRepository userRepo;
    @Autowired
    private IPlanStockageRepository planRepo;
    @Autowired
    private IEspaceStockageRepository espaceRepo;

    @Override
    public List<Subscription> GetAllSubscriptions() {
        return subscriptionRepo.findAll();
    }

    @Override
    public Subscription GetSubscription(Long id) {
        return subscriptionRepo.findById(id).orElse(null);
    }

    @Override
    public Subscription addSubscription(Subscription e) {

        User user = userRepo.findById(e.getUser().getIdUser())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PlanStockage plan = planRepo.findById(e.getPlanStockage().getIdPlan())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        EspaceStockage espace = espaceRepo.findById(e.getEspace().getIdEspace())
                .orElseThrow(() -> new RuntimeException("Espace not found"));

        // Set them back to ensure they are managed by Hibernate
        e.setUser(user);
        e.setPlanStockage(plan);
        e.setEspace(espace);

        return subscriptionRepo.save(e);
    }

    @Override
    public Subscription updateSubscription(long id, Subscription e) {
        Subscription existingSub = subscriptionRepo.findById(id);
        if (existingSub != null) {
            System.out.println("Subscription found !! Updating ...");

            // Update fields
            existingSub.setAmount(e.getAmount());
            existingSub.setCurrency(e.getCurrency());
            existingSub.setPaypalPaymentId(e.getPaypalPaymentId());
            existingSub.setPaymentStatus(e.getPaymentStatus());
            existingSub.setPaidAt(e.getPaidAt());
            existingSub.setStartDate(e.getStartDate());
            existingSub.setEndDate(e.getEndDate());
          /*  existingSub.setUser(e.getUser());
            existingSub.setPlan(e.getPlan());
            existingSub.setEspace(e.getEspace());*/

            return subscriptionRepo.save(existingSub);
        } else {
            System.out.println("No Subscription found !!");
            return null;
        }
    }

    @Override
    public void DeleteSubscription(Long id) {
        subscriptionRepo.deleteById(id);
    }


    @Override
    public boolean hasActiveSubscription(Long userId) {
        Optional<Subscription[]> subs = subscriptionRepo.findActiveSubscriptionByUserId(userId);
        return subs.isPresent() && subs.get().length > 0;
    }

    @Override
    public Optional<EspaceStockage> GetActiveEspaceStockageByUserId(Long userId) {
        return subscriptionRepo.findByUserIdAndIsActiveTrue(userId)
                .map(subscription -> subscription.getEspace());

    }


    public Subscription findByEspace(Long espaceId) {
        EspaceStockage espace = espaceRepo.findByIdEspace(espaceId);
        return subscriptionRepo.findSubscriptionByEspace(espace);
    }

    @Override
    public List<SubscriptionCountByDate> getSubscriptionsLast7Days() {
        // Get today's date
        Date today = new Date();

        // Calculate 6 days before today
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_YEAR, -6);
        Date sevenDaysAgo = cal.getTime();
        List<Object[]> results = subscriptionRepo.countSubscriptionsByPaidAt(sevenDaysAgo, today);

        return results.stream()
                .map(obj -> new SubscriptionCountByDate(
                        obj[0].toString(), // assuming obj[0] is Date (paidAt day)
                        (Long) obj[1]       // and obj[1] is the count
                ))
                .collect(Collectors.toList());
    }


@Override
    public List<PlanSubscriptionCount> getPlanSubscriptionCounts() {
        List<Object[]> results = subscriptionRepo.countSubscriptionsByPlanStockage();

        return results.stream()
                .map(obj -> new PlanSubscriptionCount(
                        (String) obj[0],  // planStockage.titre
                        (Long) obj[1]     // number of subscriptions
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanSubscriptionCount> getBestPlansBySubscriptions() {
        List<Object[]> results = subscriptionRepo.findPlanSubscriptionCounts();

        return results.stream().limit(5)
                .map(obj -> new PlanSubscriptionCount(
                        (String) obj[0],
                        (Long) obj[1]
                ))
                .collect(Collectors.toList());
    }

}
