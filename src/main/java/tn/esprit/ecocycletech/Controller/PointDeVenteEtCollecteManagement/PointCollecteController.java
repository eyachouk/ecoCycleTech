package tn.esprit.ecocycletech.Controller.PointDeVenteEtCollecteManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecte;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecteDTO;
import tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement.IPointCollecteService;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/PointCollect")
public class PointCollecteController {

    private final IPointCollecteService servicePointCollect;

    public PointCollecteController(IPointCollecteService servicePointCollect) {
        this.servicePointCollect = servicePointCollect;
    }

    private static final Logger logger = LoggerFactory.getLogger(PointCollecteController.class);


    @GetMapping("/nearest")
    public ResponseEntity<?> findNearestCollectionPoint(
            @RequestParam(required = false) String adressePointCollecte,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) {

        try {
            PointCollecte nearestPoint;

            if (adressePointCollecte != null) {
                // Address-based search
                nearestPoint = servicePointCollect.findNearestAvailablePoint(adressePointCollecte);
            } else if (latitude != null && longitude != null) {
                // Coordinate-based search
                nearestPoint = servicePointCollect.findNearestAvailablePoint(latitude, longitude);
            } else {
                return ResponseEntity.badRequest()
                        .body("Either address or both latitude/longitude must be provided");
            }

            if (nearestPoint == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No available collection points found");
            }
            return ResponseEntity.ok(nearestPoint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error finding nearest point: " + e.getMessage());
        }
    }

// In PointCollecteController.java

    @GetMapping("/all")
    public ResponseEntity<List<PointCollecteDTO>> getAllCollectionPoints() {
        try {
            List<PointCollecteDTO> points = servicePointCollect.findAll();
            return ResponseEntity.ok(points);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPointCollect(@PathVariable int id) {
        try {
            PointCollecteDTO point = servicePointCollect.findById(id);
            if (point == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Collection point not found with ID: " + id);
            }
            return ResponseEntity.ok(point);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving collection point: " + e.getMessage());
        }
    }

    @PostMapping("/savePointCollect")
    public ResponseEntity<?> savePointCollect(@RequestBody PointCollecte pointCollecte) {
        System.out.println("Received PointCollecte: " + pointCollecte);
        try {
            PointCollecte savedPoint = servicePointCollect.save(pointCollecte);
            return ResponseEntity.ok(savedPoint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving collection point: " + e.getMessage());
        }
    }

    @PutMapping("/updatePointCollect")
    public ResponseEntity<?> updatePointCollect(@RequestBody PointCollecte pointCollecte) {
        try {
            PointCollecte updatedPoint = servicePointCollect.update(pointCollecte);
            return ResponseEntity.ok(updatedPoint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating collection point: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletePointCollect/{id}")
    public ResponseEntity<?> deletePointCollect(@PathVariable int id) {
        try {
            servicePointCollect.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting collection point: " + e.getMessage());
        }
    }

    @GetMapping("/map")
    public ResponseEntity<?> getPointsForMap() {
        try {
            List<Map<String, Object>> points = servicePointCollect.getAllPointsForMap();
            Map<String, Object> response = new HashMap<>();
            response.put("points", points);
            response.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving points for map: " + e.getMessage());
        }
    }


    @GetMapping("/directions")
    public ResponseEntity<?> getDirections(
            @RequestParam double userLat,
            @RequestParam double userLon,
            @RequestParam int pointId) {
        try {
            String directions = servicePointCollect.getDirectionsToPoint(userLat, userLon, pointId);
            PointCollecteDTO point = servicePointCollect.findById(pointId);

            Map<String, Object> response = new HashMap<>();
            response.put("directions", directions);
            response.put("point", point);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting directions: " + e.getMessage());
        }
    }
}