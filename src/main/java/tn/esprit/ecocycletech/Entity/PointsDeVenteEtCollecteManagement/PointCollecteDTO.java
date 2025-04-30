package tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement;

import java.time.LocalTime;
import java.util.List;

public class PointCollecteDTO {
    private int idPointCollecte;
    private String adressePointCollecte;
    private int numTelephonePointCollecte;
    private String emailPointCollecte;
    private LocalTime heureOuverturePointCollecte;
    private LocalTime heureFermeturePointCollecte;
    private int capacitePointCollecte;
    private String disponibilitePointCollecte;
    private String location;
    private Double latitude;
    private Double longitude;
    private List<String> availableDays;

    // Constructor that takes PointCollecte entity
    public PointCollecteDTO(PointCollecte point) {
        this.idPointCollecte = point.getIdPointCollecte();
        this.adressePointCollecte = point.getAdressePointCollecte();
        this.numTelephonePointCollecte = point.getNumTelephonePointCollecte();
        this.emailPointCollecte = point.getEmailPointCollecte();
        this.heureOuverturePointCollecte = point.getHeureOuverturePointCollecte();
        this.heureFermeturePointCollecte = point.getHeureFermeturePointCollecte();
        this.capacitePointCollecte = point.getCapacitePointCollecte();
        this.disponibilitePointCollecte = point.getDisponibilitePointCollecte() != null ?
                point.getDisponibilitePointCollecte().name() : null;
        this.location = point.getLocation();
        this.latitude = point.getLatitude();
        this.longitude = point.getLongitude();
        this.availableDays = point.getAvailableDays();
    }

    public int getIdPointCollecte() {
        return idPointCollecte;
    }

    public void setIdPointCollecte(int idPointCollecte) {
        this.idPointCollecte = idPointCollecte;
    }

    public String getAdressePointCollecte() {
        return adressePointCollecte;
    }

    public void setAdressePointCollecte(String adressePointCollecte) {
        this.adressePointCollecte = adressePointCollecte;
    }

    public int getNumTelephonePointCollecte() {
        return numTelephonePointCollecte;
    }

    public void setNumTelephonePointCollecte(int numTelephonePointCollecte) {
        this.numTelephonePointCollecte = numTelephonePointCollecte;
    }

    public String getEmailPointCollecte() {
        return emailPointCollecte;
    }

    public void setEmailPointCollecte(String emailPointCollecte) {
        this.emailPointCollecte = emailPointCollecte;
    }

    public LocalTime getHeureOuverturePointCollecte() {
        return heureOuverturePointCollecte;
    }

    public void setHeureOuverturePointCollecte(LocalTime heureOuverturePointCollecte) {
        this.heureOuverturePointCollecte = heureOuverturePointCollecte;
    }

    public LocalTime getHeureFermeturePointCollecte() {
        return heureFermeturePointCollecte;
    }

    public void setHeureFermeturePointCollecte(LocalTime heureFermeturePointCollecte) {
        this.heureFermeturePointCollecte = heureFermeturePointCollecte;
    }

    public int getCapacitePointCollecte() {
        return capacitePointCollecte;
    }

    public void setCapacitePointCollecte(int capacitePointCollecte) {
        this.capacitePointCollecte = capacitePointCollecte;
    }

    public String getDisponibilitePointCollecte() {
        return disponibilitePointCollecte;
    }

    public void setDisponibilitePointCollecte(String disponibilitePointCollecte) {
        this.disponibilitePointCollecte = disponibilitePointCollecte;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }
}
