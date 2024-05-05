package asik.propensik.trainnas.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import asik.propensik.trainnas.repository.PendaftaranDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Pendaftaran;
import asik.propensik.trainnas.repository.PelatihanDb;

@Service
public class PelatihanService {
    @Autowired
    PelatihanDb pelatihanDb;

    @Autowired
    private PendaftaranDb pendaftaranDb;

    public void addPelatihan(Pelatihan pelatihan) {
        pelatihanDb.save(pelatihan);
    }

    public void deletePelatihanReq(Long id) {
        var pelatihan = getPelatihanById(id);
        pelatihan.setStatusApproval(4);
        pelatihanDb.save(pelatihan);
    }

    public void deletePelatihan(Long id) {
        List<Pendaftaran> pendaftaranList = pendaftaranDb.findByPelatihan_IdPelatihan(id);
        for (Pendaftaran pendaftaran : pendaftaranList) {
            pendaftaranDb.delete(pendaftaran);
        }
        pelatihanDb.deleteById(id);
    }

    public void donePelatihan(Long id) {
        var pelatihan = getPelatihanById(id);
        pelatihan.setStatusApproval(5);
        pelatihanDb.save(pelatihan);
    }

    public Pelatihan updatePelatihan(Pelatihan pelatihanFromDto) {
        Pelatihan pelatihan = getPelatihanById(pelatihanFromDto.getIdPelatihan());

        if (pelatihan != null) {

            pelatihan.setNamaPelatihan(pelatihanFromDto.getNamaPelatihan());
            pelatihan.setDeskripsi(pelatihanFromDto.getDeskripsi());
            pelatihan.setTempat(pelatihanFromDto.getTempat());
            pelatihan.setTanggal(pelatihanFromDto.getTanggal());
            pelatihan.setNarahubung(pelatihanFromDto.getNarahubung());
            pelatihan.setTipe(pelatihanFromDto.getTipe());
            pelatihan.setStatusApproval(1);
            pelatihanDb.save(pelatihan);
        }

        return pelatihan;
    }

    public List<Pelatihan> getAllPelatihan() { // buat liat daftar list seluruh pelatihan yang ada di database
        return pelatihanDb.findAll();
    }

    public Pelatihan getPelatihanById(Long id) {
        Optional<Pelatihan> pelatihanOptional = pelatihanDb.findById(id);
        return pelatihanOptional.orElse(null);
    }

    public List<Pelatihan> findPelatihanByPendaftaranIds(List<Long> ids) {
        return pelatihanDb.findAllById(ids);
    }

    public List<Pelatihan> searchPelatihanByJudul(String searchQuery) {
        // Lakukan pencarian berdasarkan judul dengan metode yang sesuai dari repository
        // Anda
        List<Pelatihan> searchResults = pelatihanDb.findByNamaPelatihanContainingIgnoreCase(searchQuery);
        return searchResults;
    }

    public void rejectPelatihan(Long idPelatihan, String komentar) {
        Pelatihan pelatihan = getPelatihanById(idPelatihan);
        pelatihan.setKomentar(komentar);
        pelatihan.setStatusApproval(3);
        pelatihanDb.save(pelatihan);
    }

    public void approvePelatihan(Long idPelatihan) {
        Pelatihan pelatihan = getPelatihanById(idPelatihan);
        pelatihan.setStatusApproval(2);
        pelatihan.setKomentar("");
        pelatihanDb.save(pelatihan);
    }

    public List<Pelatihan> getAllApprovedPelatihan() {
        return pelatihanDb.findByStatusApprovalIn(Arrays.asList(2, 5));
    }

    public List<Pelatihan> searchApprovedPelatihanByTitle(String searchQuery) {

        return pelatihanDb.findByNamaPelatihanContainingIgnoreCaseAndStatusApproval(searchQuery, 2);
    }

    public List<Pelatihan> getTakaPelatihan() {
        // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
        List<Integer> statusList = Arrays.asList(2, 5);
        return pelatihanDb.findByTipeAndStatusApprovalIn("Gernastastaka", statusList);
    }

    public List<Pelatihan> getTabaPelatihan() {
        // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
        List<Integer> statusList = Arrays.asList(2, 5);
        return pelatihanDb.findByTipeAndStatusApprovalIn("Gernastastaba", statusList);
    }

    public List<Pelatihan> getTakaPelatihanTrainer() {
        // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
        List<Integer> statusList = Arrays.asList(1, 2, 3, 4, 5);
        return pelatihanDb.findByTipeAndStatusApprovalIn("Gernastastaka", statusList);
    }

    public List<Pelatihan> getTabaPelatihanTrainer() {
        // Daftar status yang diinginkan: 2 (approved) dan 5 (done)
        List<Integer> statusList = Arrays.asList(1, 2, 3, 4, 5);
        return pelatihanDb.findByTipeAndStatusApprovalIn("Gernastastaba", statusList);
    }

}