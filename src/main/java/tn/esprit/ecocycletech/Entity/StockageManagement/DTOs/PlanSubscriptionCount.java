package tn.esprit.ecocycletech.Entity.StockageManagement.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PlanSubscriptionCount implements Serializable {
    private String planName;
    private Long subscriptionCount;


    public PlanSubscriptionCount(String plan, Long count) {
        this.planName = plan;
        this.subscriptionCount = count;
    }
    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Long getSubscriptionCount() {
        return subscriptionCount;
    }

    public void setSubscriptionCount(Long subscriptionCount) {
        this.subscriptionCount = subscriptionCount;
    }
}