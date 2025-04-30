package tn.esprit.ecocycletech.Controller.PointDeVenteEtCollecteManagement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointVente;
import tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement.IPointVenteService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/PointVente")

public class PointVenteController {
    IPointVenteService servicePointVente;
        @PostMapping("/savePointVente")
        public PointVente savePointVente(@RequestBody PointVente pointVente) {
            return servicePointVente.save(pointVente);
        }
        @GetMapping("/getPointVente/{id}")
        public PointVente getPointVente(@PathVariable int id) {
            return servicePointVente.findById(id);
        }
    @GetMapping("/all")
    public List<PointVente> getAllPointVentes() {
        return servicePointVente.findAll();
    }
    @PutMapping("/updatePointVente")
    public PointVente updatePointVente(@RequestBody PointVente pointVente) {
        return servicePointVente.update(pointVente);
    }

    @DeleteMapping("/deletePointVente/{id}")
    public void deletePointVente(@PathVariable int id) {
        PointVente pointVente = servicePointVente.findById(id);
        if (pointVente != null) {
            servicePointVente.delete(pointVente);
        }
    }

}
