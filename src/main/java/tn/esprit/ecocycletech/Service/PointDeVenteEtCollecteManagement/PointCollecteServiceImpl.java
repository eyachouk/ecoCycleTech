package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecte;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecteDTO;
import tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement.IPointCollecteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PointCollecteServiceImpl implements IPointCollecteService {

    private final IPointCollecteRepository pointCollecteRepository;
    private final LocationService locationService; 



    public PointCollecteServiceImpl(IPointCollecteRepository pointCollecteRepository, LocationService locationService) {
        this.pointCollecteRepository = pointCollecteRepository;
        this.locationService = locationService;
    }
    @Override
    public PointCollecte findNearestAvailablePoint(String adressePointCollecte) {
        try {
            // Convert address to coordinates
            LocationService.Coordinates coords = locationService.convertAddressToCoordinates(adressePointCollecte);

            // Then use existing distance calculation
            return findNearestAvailablePoint(coords.getLatitude(), coords.getLongitude());
        } catch (Exception e) {
            throw new RuntimeException("Failed to find nearest point by address: " + e.getMessage(), e);
        }
    }

    @Override
    public PointCollecte findNearestAvailablePoint(double userLat, double userLon) {
        // Get all collection points instead of just available ones
        List<PointCollecte> allPoints = pointCollecteRepository.findAll();

        // Initialize variables to track the nearest point
        PointCollecte nearestPoint = null;
        double minDistance = Double.MAX_VALUE;

        // Calculate distance to each point and find the nearest one
        for (PointCollecte point : allPoints) {
            double distance = calculateDistance(userLat, userLon,
                    point.getLatitude(), point.getLongitude());

            if (distance < minDistance) {
                minDistance = distance;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    @Override
    public List<PointCollecteDTO> findAll() {
        return pointCollecteRepository.findAll().stream()
                .map(PointCollecteDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public PointCollecteDTO findById(Integer id) {
        return pointCollecteRepository.findById(id)
                .map(PointCollecteDTO::new)
                .orElse(null);
    }

    @Transactional
    public PointCollecte save(PointCollecte pointCollecte) {
        return pointCollecteRepository.save(pointCollecte);
    }

    @Override
    public PointCollecte update(PointCollecte pointCollecte) {
        if (pointCollecte == null || pointCollecte.getIdPointCollecte() == 0) {
            throw new IllegalArgumentException("PointCollecte or its ID cannot be null/zero");
        }
        return pointCollecteRepository.findById(pointCollecte.getIdPointCollecte())
                .map(existing -> {
                    existing.setAdressePointCollecte(pointCollecte.getAdressePointCollecte());
                    existing.setNumTelephonePointCollecte(pointCollecte.getNumTelephonePointCollecte());
                    existing.setEmailPointCollecte(pointCollecte.getEmailPointCollecte());
                    existing.setHeureOuverturePointCollecte(pointCollecte.getHeureOuverturePointCollecte());
                    existing.setHeureFermeturePointCollecte(pointCollecte.getHeureFermeturePointCollecte());
                    existing.setCapacitePointCollecte(pointCollecte.getCapacitePointCollecte());
                    existing.setDisponibilitePointCollecte(pointCollecte.getDisponibilitePointCollecte());
                    existing.setLatitude(pointCollecte.getLatitude());
                    existing.setLongitude(pointCollecte.getLongitude());
                    existing.setAvailableDays(pointCollecte.getAvailableDays());
                    return pointCollecteRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PointCollecte not found with ID: " + pointCollecte.getIdPointCollecte()));
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID provided for deletion");
        }
        pointCollecteRepository.deleteById(id);
    }

    @Override
    public String getDirectionsToPoint(double userLat, double userLon, int pointId) throws Exception {
        PointCollecte point = pointCollecteRepository.findById(pointId)
                .orElseThrow(() -> new RuntimeException("Point not found with ID: " + pointId));

        String origin = userLat + "," + userLon;
        String destination = point.getLatitude() + "," + point.getLongitude();
        return String.format("https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=%s;%s",
                origin, destination);
    }


    @Override
    public List<Map<String, Object>> getAllPointsForMap() {
        List<PointCollecte> points = pointCollecteRepository.findAll();
        return points.stream().map(point -> {
            Map<String, Object> pointData = new HashMap<>();
            pointData.put("id", point.getIdPointCollecte());
            pointData.put("name", point.getAdressePointCollecte());
            pointData.put("latitude", point.getLatitude());
            pointData.put("longitude", point.getLongitude());
            pointData.put("address", point.getAdressePointCollecte());
            pointData.put("phone", point.getNumTelephonePointCollecte());
            pointData.put("email", point.getEmailPointCollecte());
            pointData.put("openingTime", point.getHeureOuverturePointCollecte());
            pointData.put("closingTime", point.getHeureFermeturePointCollecte());
            pointData.put("capacity", point.getCapacitePointCollecte());
            pointData.put("status", getPointStatus(point));
            pointData.put("availableDays", point.getAvailableDays());
            return pointData;
        }).toList();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private String getPointStatus(PointCollecte point) {
        LocalTime now = LocalTime.now();
        if (now.isBefore(point.getHeureOuverturePointCollecte()) ||
                now.isAfter(point.getHeureFermeturePointCollecte())) {
            return "CLOSED";
        }
        return point.getDisponibilitePointCollecte() != null ?
                point.getDisponibilitePointCollecte().name() : "AVAILABLE";
    }
}