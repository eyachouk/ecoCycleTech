package tn.esprit.ecocycletech.Controller.AppareilsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Service.AppareilsManagement.RecommendationService;

import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RecommendationSystem {
    @Autowired
    private RecommendationService recommendationService;

    @PostMapping("/recommend")
    public String recommend(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        return recommendationService.getRecommendations(query);
    }
}


