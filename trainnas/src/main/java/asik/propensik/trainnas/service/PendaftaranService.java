package asik.propensik.trainnas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asik.propensik.trainnas.dto.request.DaftarPelatihanDTO;
import asik.propensik.trainnas.dto.request.UpdatePelatihanRequestDTO;
import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Pendaftaran;
import asik.propensik.trainnas.repository.PelatihanDb;
import asik.propensik.trainnas.repository.PendaftaranDb;

@Service
public class PendaftaranService {
    @Autowired
    PendaftaranDb pendaftaranDb;

    @Autowired
    PelatihanService pelatihanService;

    public void daftarPelatihan(Long id, DaftarPelatihanDTO daftarDTO) {
        Pelatihan pelatihan = pelatihanService.getPelatihanById(id);
        Pendaftaran pendaftaran = new Pendaftaran();
        pendaftaran.setPelatihan(pelatihan);
        pendaftaran.setNamaLengkap(daftarDTO.getNamaLengkap());
        pendaftaran.setAsalSekolah(daftarDTO.getAsalSekolah());
        pendaftaran.setEmail(daftarDTO.getEmail());
        pendaftaran.setNoTelepon(daftarDTO.getNoTelepon());
        pendaftaranDb.save(pendaftaran);

    }

    public List<Long> findDistinctPelatihanId() {
        return pendaftaranDb.findDistinctPelatihanId();
    }

    public List<Pendaftaran> getPelatihanByAsalSekolah(String asalSekolah) {
        return pendaftaranDb.findByAsalSekolah(asalSekolah);
    }

    public List<Pendaftaran> getPelatihanByEmail(String email) {
        return pendaftaranDb.findByEmail(email);
    }

    public List<Pendaftaran> getTakaPelatihanSaya(String asalSekolah) {
        return pendaftaranDb.findByAsalSekolahAndPelatihan_Tipe(asalSekolah, "Gernastastaka");
    }

    public List<Pendaftaran> getTabaPelatihanSaya(String asalSekolah) {
        return pendaftaranDb.findByAsalSekolahAndPelatihan_Tipe(asalSekolah, "Gernastastaba");
    }

    public void cancelPendaftaran(Long idPendaftaran) {
        // Menghapus pendaftaran berdasarkan ID pendaftaran
        pendaftaranDb.deleteById(idPendaftaran);
    }

    public List<Pendaftaran> searchPelatihanByAsalSekolahAndNama(String asalSekolah, String namaPelatihan) {
        List<Pendaftaran> listPendaftaran = pendaftaranDb
                .findByAsalSekolahAndPelatihanNamaPelatihanContainingIgnoreCase(asalSekolah, namaPelatihan);
        return listPendaftaran;
    }

    public long countPendaftaranByTipePelatihan(String tipe) {
        // Menghitung jumlah pendaftaran berdasarkan tipe pelatihan
        return pendaftaranDb.countByPelatihan_Tipe(tipe);
    }

    public List<Map<String, Object>> countPendaftarPerPelatihan() {
        List<Object[]> results = pendaftaranDb.countPendaftarPerPelatihan();
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("pelatihan", result[0]);
            data.put("jumlah", result[1]);
            resultList.add(data);
        }

        return resultList;
    }

    public List<Map<String, Object>> countPendaftarPerBulan() {
        List<Object[]> results = pendaftaranDb.countPendaftarPerBulan();
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            int bulan = (int) result[0];
            String namaBulan = getNamaBulan(bulan); // Mendapatkan nama bulan berdasarkan nomor bulan
            long jumlah = (long) result[1];
            data.put("nama bulan", namaBulan);
            data.put("jumlah", jumlah);
            resultList.add(data);
        }

        return resultList;
    }

    // Method untuk mendapatkan nama bulan berdasarkan nomor bulan
    private String getNamaBulan(int bulan) {
        String[] namaBulan = { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September",
                "Oktober", "November", "Desember" };
        return namaBulan[bulan - 1];
    }

}