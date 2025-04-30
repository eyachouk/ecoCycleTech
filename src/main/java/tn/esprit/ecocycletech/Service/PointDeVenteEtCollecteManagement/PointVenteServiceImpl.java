package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointVente;
import tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement.IPointVenteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PointVenteServiceImpl implements IPointVenteService {

    private IPointVenteRepository pointVenteRepository;

    public PointVenteServiceImpl(IPointVenteRepository pointVenteRepository) {
        this.pointVenteRepository = pointVenteRepository;
    }
    @Override
    public PointVente findById(Integer id) {
        return pointVenteRepository.findById(id).orElse(null);
    }

    @Override
    public List<PointVente> findAll() {
        return (List<PointVente>) pointVenteRepository.findAll();
    }

    @Override
    public PointVente save(PointVente pointVente) {
        pointVenteRepository.save(pointVente);

        return pointVente;    }

    @Override
    public PointVente update(PointVente pointVente) {
        if (pointVente == null || pointVente.getIdPointVente() == 0) {
            throw new IllegalArgumentException("PointVente or its ID cannot be null/zero");
        }

        Optional<PointVente> existingPointVente = pointVenteRepository.findById(pointVente.getIdPointVente());

        if (existingPointVente.isPresent()) {
            PointVente updatedPointVente = existingPointVente.get();
            updatedPointVente.setAdressePointVente(pointVente.getAdressePointVente());
            updatedPointVente.setHeureOuverturePointVente(pointVente.getHeureOuverturePointVente());
            updatedPointVente.setHeureFermeturePointVente(pointVente.getHeureFermeturePointVente());
            updatedPointVente.setNumTelephonePointVente(pointVente.getNumTelephonePointVente());
            updatedPointVente.setEmailPointVente(pointVente.getEmailPointVente());

            return pointVenteRepository.save(updatedPointVente);
        } else {
            throw new RuntimeException("PointVente not found with ID: " + pointVente.getIdPointVente());
        }    }

    @Override
    public void delete(PointVente pointVente) {
        pointVenteRepository.delete(pointVente);

    }
}
