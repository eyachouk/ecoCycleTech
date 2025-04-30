package tn.esprit.ecocycletech.Controller.PointDeVenteEtCollecteManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Vehicule;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.VehiculeDTO;
import tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement.IVehiculeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Vehicule")
@RestController
public class VehiculeController {
    @Autowired
    private IVehiculeService vehiculeService;
    @PostMapping("/savevehicule")
    public Vehicule savevehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.save(vehicule);
    }
    @GetMapping("/getVehicule/{id}")

    public Vehicule getVehicule(@PathVariable int id) {
        return vehiculeService.findById(id);
    }
    @GetMapping("/all")
    public ResponseEntity<List<VehiculeDTO>> getAllVehicule() {
        List<VehiculeDTO> vehicules = vehiculeService.findAll();
        return ResponseEntity.ok(vehicules);
    }
    @PutMapping("/updateVehicule")
    public Vehicule updateVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.update(vehicule);
    }

    @DeleteMapping("/deleteVehicule/{id}")
    public void deleteVehicule(@PathVariable int id) {
        Vehicule vehicule = vehiculeService.findById(id);
        if (vehicule != null) {
            vehiculeService.delete(vehicule);
        }
    }
    @GetMapping("/vehicules/available")
    public ResponseEntity<List<Vehicule>> getAvailableVehicules(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {

        List<Vehicule> vehicules = vehiculeService.getAvailableVehicules(date, startTime, endTime);
        return ResponseEntity.ok(vehicules);
    }

}
