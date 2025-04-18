package tn.esprit.ecocycletech.Controller.StockageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;
import tn.esprit.ecocycletech.Service.StockageManagement.IEspaceStockageService;
import tn.esprit.ecocycletech.Service.StockageManagement.IFichierService;

import java.util.List;

@RestController
@RequestMapping("/espacestockage")
@AllArgsConstructor
public class EspaceStockageController {
    @Autowired
    IEspaceStockageService espaceStockageService;


    @GetMapping("/getAllEspaces")
    public List<EspaceStockage> retrieveAllEspaces() {
        return espaceStockageService.GetAllEspaceStockages();
    }


    @GetMapping("/getEspace/{id}")
    public EspaceStockage retrieveEspace(@PathVariable("id") long id) {
        return espaceStockageService.GetEspaceStockage(id);
    }


    @GetMapping("/getEspacesByPlan/{id}")
    public List<EspaceStockage> GetEspacesByPlan(@PathVariable("id") long id) {
        return espaceStockageService.GetEspaceStockageByPlan(id);
    }

    @PostMapping("/addEspace")
    public EspaceStockage addEspace(@RequestBody EspaceStockage e) {
        return espaceStockageService.addEspaceStockage(e);
    }


    @PutMapping("/updateEspace/{id}")
    public EspaceStockage updateEspace(@PathVariable("id") long id, @RequestBody EspaceStockage espace) {

        return espaceStockageService.updateEspaceStockage(espace);
    }

    @DeleteMapping("/deleteEspace/{id}")
    public void DeleteEspace(@PathVariable("id") long id) {
        espaceStockageService.DeleteEspaceStockage(id);
    }


    @PutMapping("/blockEspace/{id}")
    public EspaceStockage blockEspace(@PathVariable("id") long id) {

        return espaceStockageService.blockEspaceStockage(espaceStockageService.GetEspaceStockage(id));
    }

    @PutMapping("/unblockEspace/{id}")
    public EspaceStockage unblockEspace(@PathVariable("id") long id) {

        return espaceStockageService.unblockEspaceStockage(espaceStockageService.GetEspaceStockage(id));
    }
}
