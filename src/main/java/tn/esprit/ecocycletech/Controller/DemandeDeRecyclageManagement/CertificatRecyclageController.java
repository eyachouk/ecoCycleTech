package tn.esprit.ecocycletech.Controller.DemandeDeRecyclageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CertificatRecyclage;
import tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement.ICertificatRecyclageService;

@RestController
@RequestMapping("/certificatRecyclage")
@AllArgsConstructor
public class CertificatRecyclageController {
    @Autowired
    ICertificatRecyclageService certificatRecyclageService;

    // Save a new CertificatRecyclage
    @PostMapping("/save")
    public CertificatRecyclage saveCertificat(@RequestBody CertificatRecyclage certificatRecyclage) {
        return certificatRecyclageService.addCertificatRecyclage(certificatRecyclage);
    }

    // Get a specific CertificatRecyclage by ID
    @GetMapping("/{id}")
    public CertificatRecyclage getCertificatById(@PathVariable int id) {
        return certificatRecyclageService.findById(id);
    }

    // Update an existing CertificatRecyclage
    @PutMapping("/update")
    public CertificatRecyclage updateCertificat(@RequestBody CertificatRecyclage certificatRecyclage) {
        return certificatRecyclageService.updateCertificatRecyclage(certificatRecyclage);
    }

    // Delete a CertificatRecyclage by ID
    @DeleteMapping("/remove/{id}")
    public void removeCertificat(@PathVariable int id) {
        certificatRecyclageService.removeCertificatRecyclage(id);
    }
}
