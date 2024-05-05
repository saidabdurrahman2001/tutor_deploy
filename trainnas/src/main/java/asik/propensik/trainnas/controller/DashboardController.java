package asik.propensik.trainnas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import asik.propensik.trainnas.dto.PelatihanMapper;
import asik.propensik.trainnas.dto.request.CreatePelatihanRequestDTO;
import asik.propensik.trainnas.dto.request.DaftarPelatihanDTO;
import asik.propensik.trainnas.dto.request.UpdatePelatihanRequestDTO;
import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Pendaftaran;
import asik.propensik.trainnas.model.User;
import asik.propensik.trainnas.service.PelatihanService;
import asik.propensik.trainnas.service.PendaftaranService;
import asik.propensik.trainnas.service.UserService;

@Controller
public class DashboardController {
    @Autowired
    PendaftaranService pendaftaranService;

    @GetMapping("/Dashboard-trainer")
    public String dashboard(Model model) {
        long jumlahGernastastaka = pendaftaranService.countPendaftaranByTipePelatihan("Gernastastaka");
        long jumlahGernastastaba = pendaftaranService.countPendaftaranByTipePelatihan("Gernastastaba");
        model.addAttribute("jumlahGernastastaka", jumlahGernastastaka);
        model.addAttribute("jumlahGernastastaba", jumlahGernastastaba);
        System.out.println("jumlahGernastastaka: " + jumlahGernastastaka);
        System.out.println("jumlahGernastastaba: " + jumlahGernastastaba);

        var top3 = pendaftaranService.countPendaftarPerPelatihan();
        System.out.println(top3);
        System.out.println((String) top3.get(0).get("pelatihan"));
        if (top3.size() > 2) {
            model.addAttribute("pelatihan1", (String) top3.get(0).get("pelatihan"));
            model.addAttribute("pelatihan2", (String) top3.get(1).get("pelatihan"));
            model.addAttribute("pelatihan3", (String) top3.get(2).get("pelatihan"));
            model.addAttribute("jumlah1", (Long) top3.get(0).get("jumlah"));
            model.addAttribute("jumlah2", (Long) top3.get(1).get("jumlah"));
            model.addAttribute("jumlah3", (Long) top3.get(2).get("jumlah"));
        } else {
            model.addAttribute("pelatihan1", (String) top3.get(0).get("pelatihan"));
            model.addAttribute("pelatihan2", (String) top3.get(1).get("pelatihan"));
            model.addAttribute("jumlah1", (Long) top3.get(0).get("jumlah"));
            model.addAttribute("jumlah2", (Long) top3.get(1).get("jumlah"));

        }
        model.addAttribute("top3", top3);

        var pendaftarPerBulan = pendaftaranService.countPendaftarPerBulan();

        System.out.println(pendaftarPerBulan);
        model.addAttribute("bulan1", (String) pendaftarPerBulan.get(0).get("nama bulan"));
        model.addAttribute("jumlah1", (Long) pendaftarPerBulan.get(0).get("jumlah"));

        return "trainer/dashboard";
    }

}
