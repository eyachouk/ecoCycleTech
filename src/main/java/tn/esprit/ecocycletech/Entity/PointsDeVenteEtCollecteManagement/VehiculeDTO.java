package tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement;

public class VehiculeDTO {

        private int idVehicule;
        private String marqueVehicule;
        private String modeleVehicule;
        private String nomChauffeur;
        private int numTelephoneChauffeur;
        private int capaciteVehicule;

        // Constructor with all fields
        public VehiculeDTO(int idVehicule, String marqueVehicule, String modeleVehicule,
                           String nomChauffeur, int numTelephoneChauffeur, int capaciteVehicule) {
            this.idVehicule = idVehicule;
            this.marqueVehicule = marqueVehicule;
            this.modeleVehicule = modeleVehicule;
            this.nomChauffeur = nomChauffeur;
            this.numTelephoneChauffeur = numTelephoneChauffeur;
            this.capaciteVehicule = capaciteVehicule;
        }

        // Getters and setters
        public int getIdVehicule() {
            return idVehicule;
        }

        public void setIdVehicule(int idVehicule) {
            this.idVehicule = idVehicule;
        }

        public String getMarqueVehicule() {
            return marqueVehicule;
        }

        public void setMarqueVehicule(String marqueVehicule) {
            this.marqueVehicule = marqueVehicule;
        }

        public String getModeleVehicule() {
            return modeleVehicule;
        }

        public void setModeleVehicule(String modeleVehicule) {
            this.modeleVehicule = modeleVehicule;
        }

        public String getNomChauffeur() {
            return nomChauffeur;
        }

        public void setNomChauffeur(String nomChauffeur) {
            this.nomChauffeur = nomChauffeur;
        }

        public int getNumTelephoneChauffeur() {
            return numTelephoneChauffeur;
        }

        public void setNumTelephoneChauffeur(int numTelephoneChauffeur) {
            this.numTelephoneChauffeur = numTelephoneChauffeur;
        }

        public int getCapaciteVehicule() {
            return capaciteVehicule;
        }

        public void setCapaciteVehicule(int capaciteVehicule) {
            this.capaciteVehicule = capaciteVehicule;
        }
    }

