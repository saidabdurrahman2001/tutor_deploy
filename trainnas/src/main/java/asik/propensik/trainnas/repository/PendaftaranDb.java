package asik.propensik.trainnas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import asik.propensik.trainnas.model.Pendaftaran;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PendaftaranDb extends JpaRepository<Pendaftaran, Long> {
    Optional<Pendaftaran> findById(Long idPelatihan);

    @Query("SELECT DISTINCT p.pelatihan.id FROM Pendaftaran p")
    List<Long> findDistinctPelatihanId();

    List<Pendaftaran> findByAsalSekolah(String asalSekolah);

    List<Pendaftaran> findByAsalSekolahAndPelatihanNamaPelatihanContainingIgnoreCase(String asalSekolah,
            String namaPelatihan);

    List<Pendaftaran> findByAsalSekolahAndPelatihan_Tipe(String asalSekolah, String tipe);

    List<Pendaftaran> findByPelatihan_IdPelatihan(Long id);

}
