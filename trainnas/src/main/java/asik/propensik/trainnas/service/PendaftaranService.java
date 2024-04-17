package asik.propensik.trainnas.service;

import java.util.List;
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

}