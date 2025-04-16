package tn.esprit.ecocycletech.Controller.StockageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;
import tn.esprit.ecocycletech.Service.StockageManagement.IFichierService;
import tn.esprit.ecocycletech.Service.StockageManagement.IFichierService;

import java.util.List;

@RestController
@RequestMapping("/fichier")
@AllArgsConstructor
public class FichierController {

    @Autowired
    IFichierService fichierService;


    @GetMapping("/getAllFichiers")
    public List<Fichier> retrieveAllFichiers() {
        return fichierService.GetAllFichiers();
    }


    @GetMapping("/getFichier/{id}")
    public Fichier retrieveFichier(@PathVariable("id") long id) {
        return fichierService.GetFichier(id);
    }


    @PostMapping("/addFichiers")
    public List<Fichier> addFichier(@RequestBody List<Fichier> f) {
        return fichierService.addFichiers(f);
    }

    @PostMapping("/addFichier")
    public Fichier addFichier(@RequestBody Fichier f) {
        return fichierService.addFichier(f);
    }


    @PutMapping("/updateFichier/{id}")
    public Fichier updateFichier(@PathVariable("id") long id, @RequestBody Fichier Fichier) {

        return fichierService.updateFichier(Fichier);
    }

    @DeleteMapping("/deleteFichier/{id}")
    public void DeleteFichier(@PathVariable("id") long id) {
        fichierService.DeleteFichier(id);
    }
}
