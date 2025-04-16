package tn.esprit.ecocycletech.Service.StockageManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Repository.StockageManagement.IPlanStockageRepository;

import java.util.List;

@Service
public class PlanStockageService implements IPlanStockageService {

    @Autowired
    private IPlanStockageRepository planStockageRepository;

    @Override
    public List<PlanStockage> GetAllPlansStockage() {
        return planStockageRepository.findAll();
    }

    @Override
    public PlanStockage GetPlanStockage(Long id) {
       return planStockageRepository.findByIdPlan(id);
    }

    @Override
    public PlanStockage addPlanStockage(PlanStockage e) {
        return planStockageRepository.save(e);
    }

    @Override
    public PlanStockage updatePlanStockage(PlanStockage e) {
        if (planStockageRepository.existsById(e.getIdPlan())) {
            return planStockageRepository.save(e);
        }
        return null;
    }

    @Override
    public void DeletePlanStockage(Long id) {
        if (planStockageRepository.existsById(id)) {
            planStockageRepository.deleteById(id);
        }
    }
}