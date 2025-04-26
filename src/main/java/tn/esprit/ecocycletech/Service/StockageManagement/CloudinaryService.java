package tn.esprit.ecocycletech.Service.StockageManagement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // Method to upload file to Cloudinary
    public Map uploadFile(MultipartFile file, String folder) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap(
                "folder", "EcoCycleTech/"+folder,
                "resource_type", "auto" ,
                "upload_preset", "signed_ecocycletech"
        );
        return cloudinary.uploader().upload(file.getBytes(), options);
    }

    public void deleteFile(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }


    public void createCloudinaryFolder(String folderPath) throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(0); // Ecrire 1 byte dans le fichier temporaire
        }

        Map<String, Object> uploadOptions = ObjectUtils.asMap(
                "folder", folderPath,
                "resource_type", "raw" // Assurer que le type 'raw' est bien spécifié
        );

        Map uploadResult = cloudinary.uploader().upload(tempFile, uploadOptions);

        cloudinary.uploader().destroy((String) uploadResult.get("public_id"), ObjectUtils.asMap(
                "resource_type", "raw"
        ));

        tempFile.delete();
    }



    public Map<String, Object> getFolderContents(String folderPath) throws Exception {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Map<String, Object>> filesList = new ArrayList<>();

        // 1. List files in the current folder
        Map fileResult = cloudinary.search()
                .expression("folder:" +folderPath)
                .maxResults(500)
                .execute();

        List<Map> files = (List<Map>) fileResult.get("resources");
        for (Map file : files) {
            Map<String, Object> fileInfo = new HashMap<>();
            fileInfo.put("public_id", file.get("public_id"));
            fileInfo.put("url", file.get("secure_url"));
            fileInfo.put("format", file.get("format"));
            fileInfo.put("bytes", file.get("bytes"));
            fileInfo.put("created_at", file.get("created_at"));
            filesList.add(fileInfo);
        }

        result.put("folder", folderPath);
        result.put("files", filesList);

        // 2. List and recurse into subfolders
        Map subFolderResult = cloudinary.api().subFolders(folderPath, ObjectUtils.emptyMap());
        List<Map> subFolders = (List<Map>) subFolderResult.get("folders");

        List<Map<String, Object>> subfolderContents = new ArrayList<>();
        for (Map subFolder : subFolders) {
            String subFolderPath = (String) subFolder.get("path");
            subfolderContents.add(getFolderContents(subFolderPath));
        }

        result.put("subfolders", subfolderContents);

        return result;
    }


    public void deleteCloudinaryFolder(String folderPath) throws Exception {
        Map result = cloudinary.api().deleteFolder(folderPath, ObjectUtils.emptyMap());

        if (result.containsKey("deleted")) {
            return;
        }
            throw new IOException("Failed to delete folder: " + folderPath + ". Cloudinary response: " + result);

    }
}
