package tn.esprit.ecocycletech.Service.StockageManagement;

import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;

import java.util.List;

public interface IEspaceStockageService {
    public List<EspaceStockage> GetAllEspaceStockages();
    public EspaceStockage GetEspaceStockage(Long id);
    EspaceStockage addEspaceStockage (EspaceStockage e);
    EspaceStockage updateEspaceStockage (EspaceStockage e);
    void DeleteEspaceStockage(Long id);
}
