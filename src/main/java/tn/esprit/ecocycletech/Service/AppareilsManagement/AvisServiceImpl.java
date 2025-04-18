package tn.esprit.ecocycletech.Service.AppareilsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Avis;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IAvisRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AvisServiceImpl implements IAvisService {
    @Autowired
    private IAvisRepository avisRepository;
@Override
    public Avis saveAvis(Avis avis) {
    String cleanContent = badWordsFilter.filter(avis.getContenu());
    avis.setContenu(cleanContent);
    return avisRepository.save(avis);
    }
@Override
    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }
@Override
    public Optional<Avis> getAvisById(int id) {
        return avisRepository.findById(id);
    }
@Override
    public Avis updateAvis(int id, Avis updatedAvis) {
        if (avisRepository.existsById(id)) {
            updatedAvis.setIdAvis(id);
            return avisRepository.save(updatedAvis);
        }
        return null;
    }
    @Override
    public Double getAverageRating(int idAppareil) {
        Double avg = avisRepository.findAverageRatingByAppareil(idAppareil);
        return avg != null ? avg : 0.0;
    }


    @Override
    public boolean deleteAvis(int id) {
        if (avisRepository.existsById(id)) {          avisRepository.deleteById(id);
            return true;
        }
    return false;
}
    @Autowired
    private BadWordsService badWordsFilter;



}
