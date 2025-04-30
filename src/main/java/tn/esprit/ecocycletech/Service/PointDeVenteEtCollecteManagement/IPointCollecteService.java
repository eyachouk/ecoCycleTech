package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecte;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecteDTO;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface IPointCollecteService {
    public PointCollecteDTO findById(Integer id);
    public List<PointCollecteDTO> findAll();
    public PointCollecte save(PointCollecte pointCollecte);
    public PointCollecte update(PointCollecte pointCollecte);
    public void deleteById(int id);
    /**
     * Finds the nearest available collection point to the user's location
     * @param userLat User's latitude
     * @param userLon User's longitude
     * @return The nearest available collection point
     */
    PointCollecte findNearestAvailablePoint(double userLat, double userLon);
    PointCollecte findNearestAvailablePoint(String adressePointCollecte);


    /**
     * Gets directions from user's location to a collection point
     * @param userLat User's latitude
     * @param userLon User's longitude
     ** @param pointId The target collection point
     * @return Directions as a string
     * @throws Exception If there's an error getting directions
     */

    //String getDirectionsToPoint(double userLat, double userLon, PointCollecte point) throws Exception;
     String getDirectionsToPoint(double userLat, double userLon, int pointId) throws Exception ;



        /**
         * Gets all collection points formatted for map display
         * @return List of collection points with their details
         */
    List<Map<String, Object>> getAllPointsForMap();
}
