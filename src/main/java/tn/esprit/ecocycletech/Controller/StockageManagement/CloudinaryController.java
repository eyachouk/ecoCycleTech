package tn.esprit.ecocycletech.Controller.StockageManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ecocycletech.Service.StockageManagement.CloudinaryService;

import java.io.IOException;
import java.util.Map;
@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("folder") String folder) {
        try {
            Map<String, String> result = cloudinaryService.uploadFile(file, folder);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestParam("publicId") String publicId) {
        try {
            cloudinaryService.deleteFile(publicId);
            return ResponseEntity.ok(Map.of("message", "File deleted successfully"));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }



    @PostMapping("/create-folder")
    public ResponseEntity<Map<String, String>> createFolder(@RequestParam("folder") String folderPath) {
        try {
            cloudinaryService.createCloudinaryFolder(folderPath);
            String message = "Folder '" + folderPath + "' created successfully.";
            return ResponseEntity.ok(Map.of("message", message));
        } catch (IOException e) {
            String errorMessage = "Failed to create folder: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", errorMessage));
        }
    }


    @GetMapping("/list")
    public Map<String, Object> listFolderContents(@RequestParam("folder") String folder) throws Exception {
        return cloudinaryService.getFolderContents(folder);
    }


    @DeleteMapping("/delete-folder")
    public ResponseEntity<Map<String, String>> deleteFolder(@RequestParam("folder") String folderPath) {
        try {
            cloudinaryService.deleteCloudinaryFolder(folderPath);
            return ResponseEntity.ok(Map.of("message", "Folder '" + folderPath + "' deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete folder: " + e.getMessage()));
        }
    }
}
