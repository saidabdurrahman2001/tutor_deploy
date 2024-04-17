package asik.propensik.trainnas.controller;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import asik.propensik.trainnas.model.Testimoni;
import asik.propensik.trainnas.service.TestimoniService;

import java.util.List;

@RequestMapping("/testimoni")
@Controller
public class TestimoniController {

    @Autowired
    TestimoniService testimoniService;

    @GetMapping("/listTestimoni")
    public String listTestimoni(Model model) {
        List<Testimoni> listTestimoni = testimoniService.getAllTestimoni();
        model.addAttribute("listTestimoni", listTestimoni);

        return "testimoni/listTestimoni";
    }

    @GetMapping("/listTestimoni-admin")
    public String listTestimoniAdmin(Model model) {
        List<Testimoni> listTestimoni = testimoniService.getAllTestimoni();
        model.addAttribute("listTestimoni", listTestimoni);

        return "testimoni/listTestimoni-admin";
    }

    // @PostMapping("/add")
    // public String addTestimoni(@RequestParam("komentar") String komentar,
    //         @RequestParam("nama") String nama,
    //         @RequestParam("tanggal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggal) {

    //     Testimoni testimoni = new Testimoni();
    //     testimoni.setKomentar(komentar);
    //     testimoni.setNama(nama);
    //     testimoni.setTanggal(tanggal);

    //     testimoniService.addTestimoni(testimoni);
    //     return "testimoni/success-add-testimoni";
    // }

    @PostMapping("/add")
    public String addTestimoni(@RequestParam("komentar") String komentar,
        @RequestParam("nama") String nama) {

        Testimoni testimoni = new Testimoni();
        testimoni.setKomentar(komentar);
        testimoni.setNama(nama);
    
        // Mendapatkan tanggal dan waktu saat ini
        Date tanggal = new Date();
        testimoni.setTanggal(tanggal);

        testimoniService.addTestimoni(testimoni);
        return "testimoni/success-add-testimoni";
    }

    @GetMapping("/add")
    public String addTestimoni() {
        return "testimoni/addTestimoni";
    }

    @GetMapping("/delete")
    public String deleteTestimoni(@RequestParam("id") Long id) {
        testimoniService.deleteTestimoni(id);
        return "redirect:/testimoni/listTestimoni-admin";
    }

    @GetMapping("/searchTestimoni")
    public String searchTestimoni(@RequestParam("searchQuery") String search, Model model) {

        List<Testimoni> listTestimoni;
        if (search == null || search.isEmpty()) {
            listTestimoni = testimoniService.getAllTestimoni();
        } else {
            listTestimoni = testimoniService.searchTestimoni(search);
        }
       
        model.addAttribute("listTestimoni", listTestimoni);
        return "testimoni/listTestimoni";
    }

    

}
