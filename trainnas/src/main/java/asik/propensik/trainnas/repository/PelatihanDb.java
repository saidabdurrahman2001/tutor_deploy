package asik.propensik.trainnas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asik.propensik.trainnas.model.Pelatihan;
import asik.propensik.trainnas.model.Pendaftaran;
import jakarta.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public interface PelatihanDb extends JpaRepository<Pelatihan, Long> {
    Optional<Pelatihan> findById(Long idPelatihan);

    List<Pelatihan> findByNamaPelatihanContainingIgnoreCase(String nama);

    List<Pelatihan> findByStatusApproval(int statusApproval);

    List<Pelatihan> findByNamaPelatihanContainingIgnoreCaseAndStatusApproval(String namaPelatihan, int statusApproval);

    List<Pelatihan> findByStatusApprovalIn(List<Integer> asList);

    List<Pelatihan> findByTipeAndStatusApprovalIn(String tipe, List<Integer> statusApproval);

}
