package asik.propensik.trainnas.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import asik.propensik.trainnas.dto.request.CreateSilabusRequestDTO;
import asik.propensik.trainnas.model.Silabus;
import asik.propensik.trainnas.service.SilabusService;

import java.io.File;
import java.util.*;

@RequestMapping("/silabus")
@Controller
public class SilabusController {

    @Autowired
    SilabusService silabusService;

    @GetMapping("/viewall")
    public String silabus(Model model) {
        List<Silabus> listSilabus = silabusService.getAllSilabus();
        model.addAttribute("listSilabus", listSilabus);
        return "trainee/trainee-viewall-silabus";
    }

    @GetMapping("/viewall-trainer")
    public String silabusTrainer(Model model) {
        List<Silabus> listSilabus = silabusService.getAllSilabus();
        model.addAttribute("listSilabus", listSilabus);
        return "trainer/viewall-silabus";
    }

    @GetMapping("/tambah")
    public String tambahSilabus(Model model) {
        var silabusDTO = new CreateSilabusRequestDTO();
        model.addAttribute("silabusDTO", silabusDTO);
        return "trainer/form-create-silabus";
    }

    @PostMapping("/tambah")
    public String tambahSilabus(@ModelAttribute CreateSilabusRequestDTO silabusRequestDTO,
            @RequestParam("file") MultipartFile file, @RequestParam("tingkatan") String tingkatan,
            @RequestParam("deskripsi") String deskripsi)
            throws IOException, GeneralSecurityException {
        try {
            boolean isDescriptionExists = silabusService.isDescriptionExists(deskripsi);
            if (isDescriptionExists) {
                return "duplicateDescription"; // Return an appropriate view for duplicate description
            }
            if (file.isEmpty()) {
                return "empty";
            }
            // Check file size
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB
                return "limit";
            }
        } catch (MaxUploadSizeExceededException ex) {
            return "redirect:/limit.html";
        }
        // Get original file name
        String originalFileName = file.getOriginalFilename();
        String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Change file extension to lower case for uniformity
        fileExtension = fileExtension.toLowerCase();
        System.out.println("INI FILE EXTENSION: " + fileExtension);

        // Check if file extension is supported
        List<String> supportedExtensions = Arrays.asList(".pdf", ".doc", ".docx", ".jpg", ".jpeg", ".png");
        if (!supportedExtensions.contains(fileExtension)) {
            System.out.println("Unsupported file format AMAN");
            return "unsupported";
        }

        // Set content type based on file extension
        String contentType;
        if (fileExtension.equals(".pdf")) {
            contentType = "application/pdf";
        } else if (fileExtension.equals(".doc") || fileExtension.equals(".docx")) {
            contentType = "application/msword";
        } else if (fileExtension.equals(".jpg") || fileExtension.equals(".jpeg")) {
            contentType = "image/jpeg";
        } else {
            contentType = "image/png";
        }

        File tempFile = File.createTempFile(fileNameWithoutExtension, fileExtension);
        file.transferTo(tempFile);

        // Continue with file upload
        try {
            // Call your service method to upload file to Google Drive
            // Pass contentType instead of "application/pdf"
            Silabus silabus = silabusService.uploadImageToDrive(tempFile, tingkatan, deskripsi, contentType);
            System.out.println(silabus);
            return "trainer/success-create-silabus";
        } catch (Exception e) {
            e.printStackTrace();
            return "failedUpload";
        }
    }

    @GetMapping("/detail-trainer")
    public String detailSilabusTrainer(@RequestParam("id") Long id, Model model) {
        Silabus silabus = silabusService.getSilabusById(id);
        model.addAttribute("silabus", silabus);
        return "trainer/detail-silabus";
    }

    @GetMapping("/detail")
    public String detailSilabus(@RequestParam("id") Long id, Model model) {
        Silabus silabus = silabusService.getSilabusById(id);
        model.addAttribute("silabus", silabus);
        return "trainee/detail-silabus";
    }

    @GetMapping("/update")
    public String updateSilabus(@RequestParam("id") Long id, Model model) {
        Silabus silabus = silabusService.getSilabusById(id);
        System.out.println(silabus.getIdSilabus() + " Ini adalah silabus ID 1");
        model.addAttribute("silabus", silabus);
        return "trainer/form-update-silabus";
    }

    @PostMapping("/update")
    public String updateSilabus(@ModelAttribute Silabus silabus, Model model, @RequestParam("file") MultipartFile file,
            @RequestParam("tingkatan") String tingkatan, @RequestParam("deskripsi") String deskripsi,
            @RequestParam("id") Long id)
            throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            System.out.println(silabus.getIdSilabus() + " Ini adalah silabus ID YAH");
            System.out.println(silabus.getDeskripsi() + " Ini adalah silabus DESKRIPSI YAH");
            silabusService.updateSilabus(id, tingkatan, deskripsi);

        } else {
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB
                return "limit";
            }

            String originalFileName = file.getOriginalFilename();
            String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // Change file extension to lower case for uniformity
            fileExtension = fileExtension.toLowerCase();
            System.out.println("INI FILE EXTENSION: " + fileExtension);

            // Check if file extension is supported
            List<String> supportedExtensions = Arrays.asList(".pdf", ".doc", ".docx", ".jpg", ".jpeg", ".png");
            if (!supportedExtensions.contains(fileExtension)) {
                System.out.println("Unsupported file format AMAN");
                return "unsupported";
            }

            // Set content type based on file extension
            String contentType;
            if (fileExtension.equals(".pdf")) {
                contentType = "application/pdf";
            } else if (fileExtension.equals(".doc") || fileExtension.equals(".docx")) {
                contentType = "application/msword";
            } else if (fileExtension.equals(".jpg") || fileExtension.equals(".jpeg")) {
                contentType = "image/jpeg";
            } else {
                contentType = "image/png";
            }

            File tempFile = File.createTempFile(fileNameWithoutExtension, fileExtension);
            file.transferTo(tempFile);
            silabusService.updateFile(tempFile, id, tingkatan, deskripsi, contentType);
        }
        return "trainer/success-update-silabus";
    }

    @GetMapping("/delete")
    public String deleteSilabus(@RequestParam("id") Long id) {
        silabusService.deleteSilabus(id);
        return "trainer/success-delete-silabus";
    }

    @GetMapping("/searchSilabus")
    public String searchSilabus(@RequestParam(value = "searchQuery", required = false) String search,
            @RequestParam(value = "tingkatan", required = false) String tingkatan,
            Model model) {

        List<Silabus> listSilabus;
        if ((search == null || search.isEmpty()) && (tingkatan == null || tingkatan.isEmpty())) {
            listSilabus = silabusService.getAllSilabus();
        } else if (tingkatan == null || tingkatan.isEmpty()) {
            listSilabus = silabusService.searchSilabus(search);
        } else if (search == null || search.isEmpty()) {
            listSilabus = silabusService.searchSilabusByTingkatan(tingkatan);
        } else {
            listSilabus = silabusService.searchSilabusByQueryAndTingkatan(search, tingkatan);
        }
        model.addAttribute("listSilabus", listSilabus);
        return "trainer/viewall-silabus";
    }

}
