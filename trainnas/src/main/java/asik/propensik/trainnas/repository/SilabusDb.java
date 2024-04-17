package asik.propensik.trainnas.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

import asik.propensik.trainnas.model.Silabus;

@Repository
@Transactional
public interface SilabusDb extends JpaRepository<Silabus, Long> {

    // Optional<Silabus> findByDeskripsi(String fileName);

    List<Silabus> findByDeskripsiContainingIgnoreCase(String deskripsi);

    List<Silabus> findByTingkatanIgnoreCase(String tingkatan);

}
