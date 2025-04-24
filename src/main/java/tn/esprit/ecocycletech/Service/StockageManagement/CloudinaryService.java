package tn.esprit.ecocycletech.Service.StockageManagement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // Method to upload file to Cloudinary
    public Map<String, String> uploadFile(MultipartFile file, String folder) throws IOException {
        Map<String, String> options = ObjectUtils.asMap("folder", folder);
        return cloudinary.uploader().upload(file.getBytes(), options);
    }

    public void deleteFile(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

}
