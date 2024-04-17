package asik.propensik.trainnas.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import asik.propensik.trainnas.dto.PelatihanMapper;
import asik.propensik.trainnas.dto.request.CreatePelatihanRequestDTO;
import asik.propensik.trainnas.dto.request.DaftarPelatihanDTO;
import asik.propensik.trainnas.dto.request.UpdatePelatihanRequestDTO;
import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Pendaftaran;
import asik.propensik.trainnas.service.PelatihanService;
import asik.propensik.trainnas.service.PendaftaranService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class PelatihanController {
    @Autowired
    PelatihanMapper pelatihanMapper;

    @Autowired
    PelatihanService pelatihanService;

    @Autowired
    PendaftaranService pendaftaranService;
    
    @RequestMapping("/")
    public String hello() {
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/pelatihan/add")
    public String formAddPelatihan(Model model) {
        var pelatihanDTO = new CreatePelatihanRequestDTO();
        model.addAttribute("pelatihanDTO", pelatihanDTO);
        return "trainer/form-create-pelatihan";
    }

    @PostMapping("/pelatihan/add")
    public String formAddPelatihan(@ModelAttribute CreatePelatihanRequestDTO pelatihanDTO, Model model) {
        var pelatihan = pelatihanMapper.createPelatihanRequestDTOToPelatihan(pelatihanDTO);
        pelatihanService.addPelatihan(pelatihan);
        return "trainer/succes-create-pelatihan";
    }

    @GetMapping("pelatihan/viewall")
    public String listLatihan(Model model) {

        List<Pelatihan> listPelatihan = pelatihanService.getAllApprovedPelatihan();

        model.addAttribute("listPelatihan", listPelatihan);
        return "trainee/trainee-viewall-pelatihan";

    }

    @GetMapping("pelatihan/viewall-trainer")
    public String listLatihanTrainer(Model model) {

        List<Pelatihan> listPelatihan = pelatihanService.getAllPelatihan();

        model.addAttribute("listPelatihan", listPelatihan);
        return "trainer/viewall-pelatihan";
    }

    @GetMapping("pelatihan/viewall-trainer-manager")
    public String listLatihanTrainerManager(Model model) {

        List<Pelatihan> listPelatihan = pelatihanService.getAllPelatihan();

        model.addAttribute("listPelatihan", listPelatihan);
        return "manager/viewall-pelatihan";
    }

    @GetMapping("/pelatihan/daftar")
    public String formDaftarPelatihan(Model model, @RequestParam("id") Long pelatihanId) {
        Pelatihan pelatihan = pelatihanService.getPelatihanById(pelatihanId);
        model.addAttribute("pelatihan", pelatihan);
        model.addAttribute("daftarDTO", new DaftarPelatihanDTO());
        return "trainee/trainee-daftar-pelatihan";
    }

    @PostMapping("/pelatihan/daftar")
    public String daftarPelatihan(@ModelAttribute DaftarPelatihanDTO daftarDTO,
            @RequestParam("id") Long pelatihanId) {
        // Logika untuk menyimpan data pendaftaran
        System.out.println(pelatihanId + "/n ini daftar DTO");
        System.out.println(daftarDTO.getNamaLengkap() + "/n ini nama lengkap");

        pendaftaranService.daftarPelatihan(pelatihanId, daftarDTO);
        return "trainee/succes-mendaftar-pelatihan";
    }

    @GetMapping("/pelatihan/searchTrainee")
    public String searchPelatihan(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<Pelatihan> listPelatihan;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Ambil data pelatihan yang sudah di-approve dan sesuai dengan kriteria
            // pencarian
            listPelatihan = pelatihanService.searchApprovedPelatihanByTitle(searchQuery);
        } else {
            // Ambil semua data pelatihan yang sudah di-approve
            listPelatihan = pelatihanService.getAllApprovedPelatihan();
        }
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainee/trainee-viewall-pelatihan";
    }

    @GetMapping("/pelatihan/searchDaftarSaya")
    public String searchPelatihanDaftarSaya(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<Pendaftaran> listPendaftaran;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            listPendaftaran = pendaftaranService.searchPelatihanByAsalSekolahAndNama("A", searchQuery);
        } else {
            listPendaftaran = pendaftaranService.getPelatihanByAsalSekolah("A");
        }
        model.addAttribute("listPendaftaran", listPendaftaran);
        return "trainee/daftarPelatihanSaya";
    }

    @GetMapping("/pelatihan/searchTrainer")
    public String searchTrainer(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<Pelatihan> listPelatihan;
        listPelatihan = pelatihanService.searchPelatihanByJudul(searchQuery);
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainer/viewall-pelatihan";
    }

    // @GetMapping("/pelatihan/daftarPelatihanSaya")
    // public String daftarPelatihanSaya(Model model) {
    // // Dapatkan ID pelatihan yang unik dari pendaftaran
    // List<Long> pelatihanIds = pendaftaranService.findDistinctPelatihanId(); //
    // mesti implement kalo udh ada user

    // // Gunakan ID tersebut untuk mendapatkan detail pelatihan
    // List<Pelatihan> pelatihanList =
    // pelatihanService.findPelatihanByPendaftaranIds(pelatihanIds);

    // // Tambahkan daftar pelatihan ke model untuk ditampilkan di view
    // model.addAttribute("pelatihanList", pelatihanList);

    // // Nama file HTML (tanpa ekstensi .html) di dalam folder
    // // src/main/resources/templates
    // return "daftarPelatihanSaya";
    // }

    @GetMapping("/pelatihan/daftarPelatihanSaya")
    public String daftarPelatihanSaya(Model model) {
        List<Pendaftaran> listPendaftaran = pendaftaranService.getPelatihanByAsalSekolah("A");
        model.addAttribute("listPendaftaran", listPendaftaran);
        return "trainee/daftarPelatihanSaya";
    }

    @PostMapping("/pelatihan/cancel/{id}")
    public String cancelPendaftaran(@PathVariable("id") String idPendaftaranStr) {
        Long idPendaftaran = Long.parseLong(idPendaftaranStr);
        System.out.println(idPendaftaran);
        // Menghapus pendaftaran berdasarkan ID pendaftaran
        pendaftaranService.cancelPendaftaran(idPendaftaran);
        return "redirect:/pelatihan/daftarPelatihanSaya";
    }

    @PostMapping("/pelatihan/reject")
    public String rejectPelatihan(@RequestParam("idPelatihan") Long idPelatihan,
            @RequestParam("komentar") String komentar) {
        System.out.println(idPelatihan);
        System.out.println(komentar);
        pelatihanService.rejectPelatihan(idPelatihan, komentar);
        return "redirect:/pelatihan/viewall-trainer-manager"; // Sesuaikan dengan halaman yang diinginkan setelah
                                                              // penolakan
    }

    @PostMapping("/pelatihan/approve")
    public String approvePelatihan(@RequestParam("id") Long idPelatihan) {
        // Lakukan logika untuk mengubah statusApproval menjadi approved
        pelatihanService.approvePelatihan(idPelatihan);
        return "redirect:/pelatihan/viewall-trainer-manager";
    }

    // please make controller for detail pelatihan
    @GetMapping("/pelatihan/detail")
    public String detailPelatihan(Model model, @RequestParam("id") Long idPelatihan) {
        var pelatihan = pelatihanService.getPelatihanById(idPelatihan);
        model.addAttribute("pelatihan", pelatihan);
        // Lakukan logika untuk mengambil detail pelatihan berdasarkan ID
        return "trainee/detailPelatihan"; // Sesuaikan dengan halaman yang diinginkan
    }

    @GetMapping("/pelatihan/detail-trainer")
    public String detailPelatihanTrainer(Model model, @RequestParam("id") Long idPelatihan) {
        var pelatihan = pelatihanService.getPelatihanById(idPelatihan);
        model.addAttribute("pelatihan", pelatihan);
        return "trainer/detailPelatihan"; // Sesuaikan dengan halaman yang diinginkan
    }

    @GetMapping("/pelatihan/update")
    public String formUpdatePelatihan(Model model, @RequestParam("id") Long idPelatihan) {
        var pelatihan = pelatihanService.getPelatihanById(idPelatihan);
        System.out.println("ini adalah pelatihan" + pelatihan.getNamaPelatihan());
        var pelatihanDTO = new CreatePelatihanRequestDTO();
        model.addAttribute("pelatihan", pelatihan);
        model.addAttribute("pelatihanDTO", pelatihanDTO);
        return "trainer/form-update-pelatihan";
    }

    // @PostMapping("/pelatihan/update")
    // public String updatePelatihan(@ModelAttribute UpdatePelatihanRequestDTO
    // pelatihanDTO,
    // @RequestParam("id") Long idPelatihan, Model model) {
    // var pelatihanFromDto =
    // pelatihanMapper.updatePelatihanRequestDTOToPelatihan(pelatihanDTO);
    // var pelatihan = pelatihanService.updatePelatihan(pelatihanFromDto);
    // model.addAttribute("pelatihan", pelatihan);
    // return "redirect:/pelatihan/viewall-trainer";
    // }

    @PostMapping("/pelatihan/update")
    public String updatePelatihan(Model model, @RequestParam("id") Long idPelatihan,
            @RequestParam("namaPelatihan") String namaPelatihan,
            @RequestParam("tipe") String tipe,
            @RequestParam("tempat") String tempat,
            @RequestParam("deskripsi") String deskripsi,
            @RequestParam("narahubung") String narahubung,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("tanggal") Date tanggal) {

        var pelatihanDTO = new UpdatePelatihanRequestDTO();
        pelatihanDTO.setIdPelatihan(idPelatihan);
        pelatihanDTO.setNamaPelatihan(namaPelatihan);
        pelatihanDTO.setTipe(tipe);
        pelatihanDTO.setTempat(tempat);
        pelatihanDTO.setDeskripsi(deskripsi);
        pelatihanDTO.setNarahubung(narahubung);
        pelatihanDTO.setTanggal(new java.sql.Date(tanggal.getTime()));

        System.out.println(pelatihanDTO.getIdPelatihan() + "ini adalah id pelatihan");

        var pelatihanFromDto = pelatihanMapper.updatePelatihanRequestDTOToPelatihan(pelatihanDTO);
        System.out.println(pelatihanFromDto.getNamaPelatihan() + "ini adalah nama pelatihan");
        System.out.println(pelatihanFromDto.getIdPelatihan() + "ini adalah idpelatihan");

        var pelatihan = pelatihanService.updatePelatihan(pelatihanFromDto);
        model.addAttribute("pelatihan", pelatihan);

        return "redirect:/pelatihan/viewall-trainer";
    }

    @GetMapping("/pelatihan/delete")
    public String deletePelatihan(@RequestParam("id") Long idPelatihan) {
        pelatihanService.deletePelatihanReq(idPelatihan);
        return "redirect:/pelatihan/viewall-trainer";
    }

    @PostMapping("/pelatihan/delete")
    public String deletePelatihan(@RequestParam("id") Long idPelatihan, Model model) {
        pelatihanService.deletePelatihan(idPelatihan);
        return "redirect:/pelatihan/viewall-trainer";
    }

    @PostMapping("/pelatihan/done")
    public String donePelatihan(@RequestParam("id") Long idPelatihan) {
        pelatihanService.donePelatihan(idPelatihan);
        return "redirect:/pelatihan/viewall-trainer-manager";
    }

    @GetMapping("/pelatihan/taka")
    public String takaPelatihan(Model model) {
        List<Pelatihan> listPelatihan = pelatihanService.getTakaPelatihan();
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainee/trainee-viewall-pelatihan";
    }

    @GetMapping("/pelatihan/taba")
    public String tabaPelatihan(Model model) {
        List<Pelatihan> listPelatihan = pelatihanService.getTabaPelatihan();
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainee/trainee-viewall-pelatihan";
    }

    @GetMapping("/pelatihan/takaSaya")
    public String takaPelatihanSaya(Model model) {
        List<Pendaftaran> listPendaftaran = pendaftaranService.getTakaPelatihanSaya("A");
        System.out.println(listPendaftaran.size() + "ini adalah size");
        model.addAttribute("listPendaftaran", listPendaftaran);
        return "trainee/daftarPelatihanSaya";
    }

    @GetMapping("/pelatihan/tabaSaya")
    public String tabaPelatihanSaya(Model model) {
        List<Pendaftaran> listPendaftaran = pendaftaranService.getTabaPelatihanSaya("A");
        model.addAttribute("listPendaftaran", listPendaftaran);
        return "trainee/daftarPelatihanSaya";
    }

    // @GetMapping("/pelatihan/daftarPelatihanSaya")
    // public String daftarPelatihanSaya(Model model) {
    // List<Pendaftaran> listPendaftaran =
    // pendaftaranService.getPelatihanByAsalSekolah("A");
    // model.addAttribute("listPendaftaran", listPendaftaran);
    // return "trainee/daftarPelatihanSaya";
    // }

    @GetMapping("/pelatihan/takaTrainer")
    public String takaPelatihanTrainer(Model model) {
        List<Pelatihan> listPelatihan = pelatihanService.getTakaPelatihanTrainer();
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainer/viewall-pelatihan";
    }

    @GetMapping("/pelatihan/tabaTrainer")
    public String tabaPelatihanTrainer(Model model) {
        List<Pelatihan> listPelatihan = pelatihanService.getTabaPelatihanTrainer();
        model.addAttribute("listPelatihan", listPelatihan);
        return "trainer/viewall-pelatihan";
    }

    // @GetMapping("pelatihan/viewall")
    // public String listLatihan(Model model) {

    // List<Pelatihan> listPelatihan = pelatihanService.getAllApprovedPelatihan();

    // model.addAttribute("listPelatihan", listPelatihan);
    // return "trainee/trainee-viewall-pelatihan";

    // }
    @GetMapping("/pelatihan/filterPelatihanTrainee")
    public String filterPelatihanTrainee(@RequestParam("sortType") String sortType, Model model) {
        System.out.println("masuk filter");
        if("All".equals(sortType)){
            List<Pelatihan> listPelatihan = pelatihanService.getAllApprovedPelatihan();
            model.addAttribute("listPelatihan", listPelatihan);
        } else if ("Gernastastaka".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getTakaPelatihan();
            model.addAttribute("listPelatihan", listPelatihan);
        } else if("Gernastastaba".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getTabaPelatihan();
            model.addAttribute("listPelatihan", listPelatihan);
        }
        return "trainee/trainee-viewall-pelatihan";
    }
    @GetMapping("/pelatihan/filterPelatihanTrainer")
    public String filterPelatihanTrainer(@RequestParam("sortType") String sortType, Model model) {
        System.out.println("masuk filter");
        if("All".equals(sortType)){
            List<Pelatihan> listPelatihan = pelatihanService.getAllPelatihan();
            model.addAttribute("listPelatihan", listPelatihan);
        } else if ("Gernastastaka".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getTakaPelatihanTrainer();
            model.addAttribute("listPelatihan", listPelatihan);
        } else if("Gernastastaba".equals(sortType)) {
            List<Pelatihan> listPelatihan = pelatihanService.getTabaPelatihanTrainer();
            model.addAttribute("listPelatihan", listPelatihan);
        }
        return "trainer/viewall-pelatihan";
    }

    @GetMapping("/pelatihan/filterDaftarPelatihanSaya")
    public String filterDaftarPelatihanSaya(@RequestParam("sortType") String sortType, Model model) {
        System.out.println("masuk filter");
        if("All".equals(sortType)){
            var listPendaftaran = pendaftaranService.getPelatihanByAsalSekolah("A");
            model.addAttribute("listPendaftaran", listPendaftaran);
        } else if ("Gernastastaka".equals(sortType)) {
            var listPendaftaran = pendaftaranService.getTakaPelatihanSaya("A");
            model.addAttribute("listPendaftaran", listPendaftaran);
        } else if("Gernastastaba".equals(sortType)) {
            var listPendaftaran = pendaftaranService.getTabaPelatihanSaya(  "A");
            model.addAttribute("listPendaftaran", listPendaftaran);
        }
        return "trainee/daftarPelatihanSaya";
    }
}
