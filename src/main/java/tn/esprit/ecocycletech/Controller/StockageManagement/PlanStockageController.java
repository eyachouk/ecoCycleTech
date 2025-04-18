package tn.esprit.ecocycletech.Controller.StockageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Repository.StockageManagement.IPlanStockageRepository;
import tn.esprit.ecocycletech.Service.StockageManagement.IPlanStockageService;
import tn.esprit.ecocycletech.Service.StockageManagement.PlanStockageService;

import java.util.List;

@RestController
@RequestMapping("/planstockage")
@AllArgsConstructor
public class PlanStockageController {
    @Autowired
    IPlanStockageService planStockageService;


    @GetMapping("/getAllPlans")
    public List<PlanStockage> retrieveAllPlans() {
        return planStockageService.GetAllPlansStockage();
    }


    @GetMapping("/getPlan/{id}")
    public PlanStockage retrievePlan(@PathVariable("id") long id) {
        return planStockageService.GetPlanStockage(id);
    }


    @PostMapping("/addPlan")
    public PlanStockage addPlan(@RequestBody PlanStockage p) {
        return planStockageService.addPlanStockage(p);
    }


    @PutMapping("/updatePlan/{id}")
    public PlanStockage updatePlan(@PathVariable("id") long id,@RequestBody PlanStockage plan) {

        return planStockageService.updatePlanStockage(id,plan);
    }

    @DeleteMapping("/deletePlan/{id}")
    public void DeletePlan(@PathVariable("id") long id) {
        planStockageService.DeletePlanStockage(id);
    }
}
