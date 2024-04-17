package asik.propensik.trainnas.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.http.FileContent;

import java.io.IOException;
import java.util.*;

import asik.propensik.trainnas.model.Silabus;
import asik.propensik.trainnas.repository.SilabusDb;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

@Service
public class SilabusService {
    @Autowired
    SilabusDb silabusDb;

    private final String FOLDER_PATH = "C:/Users/User/Desktop/nyingnying/";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoolgeCredentials();

    private static String getPathToGoolgeCredentials() {
        String currentDir = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDir, "cred.json");
        return filePath.toString();
    }

    public List<Silabus> getAllSilabus() {
        return silabusDb.findAll();
    }

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String folderId = "d";
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Silabus fileData = silabusDb.save(Silabus.builder()
                .deskripsi(file.getOriginalFilename())
                .tingkatan(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    public Silabus uploadImageToDrive(File file, String tingkatan, String deskripsi, String contentType)
            throws GeneralSecurityException, IOException {
        Silabus silabus = new Silabus();
        try {
            String folderId = "1y0ADpeeEcvnoT-W91m7OzV8SQDF2p77T";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent(contentType, file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String fileUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("File URL: " + fileUrl);
            file.delete();
            silabus.setTingkatan(tingkatan);
            silabus.setDeskripsi(deskripsi);
            silabus.setFilePath(fileUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        silabusDb.save(silabus);
        return silabus;
    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {
        // Load the service account key JSON file
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        // Create a new HttpCredentialsAdapter instance with the credentials
        HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(credentials);

        // Create and return the Drive service
        return new Drive.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                adapter)
                .build();
    }

    public Silabus getSilabusById(Long id) {
        return silabusDb.findById(id).get();
    }

    public void updateSilabus(Long id, String tingkatan, String deskripsi) {
        Silabus silabus1 = silabusDb.findById(id).get();
        silabus1.setTingkatan(tingkatan);
        silabus1.setDeskripsi(deskripsi);
        silabusDb.save(silabus1);
    }

    public void updateFile(File file, Long id, String tingkatan, String deskripsi, String contentType) {
        Silabus silabus1 = silabusDb.findById(id).get();
        try {
            String folderId = "1u63WVxUcGMCY9XRGrK-G7xE0R4wwe_9s";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent(contentType, file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String fileUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("File URL: " + fileUrl);
            file.delete();
            silabus1.setTingkatan(tingkatan);
            silabus1.setDeskripsi(deskripsi);
            silabus1.setFilePath(fileUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        silabusDb.save(silabus1);
    }

    public void deleteSilabus(Long id) {
        Silabus silabus = silabusDb.findById(id).get();
        silabusDb.delete(silabus);
    }

    public List<Silabus> searchSilabus(String search) {
        return silabusDb.findByDeskripsiContainingIgnoreCase(search);
    }

    public List<Silabus> searchSilabusByTingkatan(String tingkatan) {
        return silabusDb.findByTingkatanIgnoreCase(tingkatan);
    }

    public List<Silabus> searchSilabusByQueryAndTingkatan(String searchQuery, String tingkatan) {
        List<Silabus> byQuery = silabusDb.findByDeskripsiContainingIgnoreCase(searchQuery);
        List<Silabus> byTingkatan = silabusDb.findByTingkatanIgnoreCase(tingkatan);

        // Gabungkan hasil pencarian dari kedua kriteria
        byQuery.retainAll(byTingkatan);

        return byQuery;
    }

    public boolean isDescriptionExists(String deskripsi) {
        List<Silabus> existingSilabus = silabusDb.findByDeskripsiContainingIgnoreCase(deskripsi);
        return !existingSilabus.isEmpty();
    }

}