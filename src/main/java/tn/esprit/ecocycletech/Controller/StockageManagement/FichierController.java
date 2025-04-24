package tn.esprit.ecocycletech.Controller.StockageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;
import tn.esprit.ecocycletech.Service.StockageManagement.CloudinaryService;
import tn.esprit.ecocycletech.Service.StockageManagement.IFichierService;
import tn.esprit.ecocycletech.Service.StockageManagement.IFichierService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fichier")
@AllArgsConstructor
public class FichierController {

    @Autowired
    IFichierService fichierService;
    @Autowired
    CloudinaryService cloudinaryservice;


    @GetMapping("/getAllFichiers")
    public List<Fichier> retrieveAllFichiers() {
        return fichierService.GetAllFichiers();
    }


    @GetMapping("/getFichier/{id}")
    public Fichier retrieveFichier(@PathVariable("id") long id) {
        return fichierService.GetFichier(id);
    }


    @PostMapping("/addFichiers")
    public List<Fichier> addFichier(@RequestBody List<Fichier> f) {
        return fichierService.addFichiers(f);
    }

    @PostMapping("/addFichier")
    public Fichier addFichier(@RequestBody Fichier f) {
        return fichierService.addFichier(f);
    }


    @PutMapping("/updateFichier/{id}")
    public Fichier updateFichier(@PathVariable("id") long id, @RequestBody Fichier Fichier) {

        return fichierService.updateFichier(Fichier);
    }

    @DeleteMapping("/deleteFichier/{id}")
    public void DeleteFichier(@PathVariable("id") long id) {
        fichierService.DeleteFichier(id);
    }


    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("folder") String folder) {
        try {
            Map<String, String> uploadResult = cloudinaryservice.uploadFile(file, folder);
            return ResponseEntity.ok(uploadResult);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "File upload failed"));
        }
    }

    @DeleteMapping("/deletecloud/{publicId}")
    public ResponseEntity<String> deleteFile(@PathVariable String publicId) {
        try {
            cloudinaryservice.deleteFile(publicId);
            return ResponseEntity.ok("File deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File deletion failed");
        }
    }


}
