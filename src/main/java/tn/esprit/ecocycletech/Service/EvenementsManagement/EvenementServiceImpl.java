package tn.esprit.ecocycletech.Service.EvenementsManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;
import tn.esprit.ecocycletech.Repository.EvenementsManagement.IEvenementRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EvenementServiceImpl implements IEvenementService {
    @Autowired
    IEvenementRepository eventrepository;
    @Override
    public Evenement save(Evenement event) {
        return eventrepository.save(event);
    }

    @Override
    public Evenement findById(int id) {return eventrepository.findByIdEvenement(id);
    }

    @Override
    public void delete(int id) {
        eventrepository.deleteById(id);
    }

    @Override
    public Evenement update(Evenement event) {
        Evenement e = eventrepository.findByIdEvenement(event.getIdEvenement());
        if (e != null) {
            return eventrepository.save(event);
        }
        return null;
    }

    @Override
    public Evenement add(Evenement event) {
        return eventrepository.save(event);
    }

    @Override
    public List<Evenement> retrieveAllEvenements() {
        return eventrepository.findAll();
    }
}
