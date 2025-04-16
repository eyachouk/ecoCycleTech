package tn.esprit.ecocycletech.Service.StockageManagement;

import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;

import java.util.List;

public interface IPlanStockageService {
    public List<PlanStockage> GetAllPlansStockage();
    public PlanStockage GetPlanStockage(Long id);
    PlanStockage addPlanStockage (PlanStockage e);
    PlanStockage updatePlanStockage (PlanStockage e);
    void DeletePlanStockage(Long id);
}
