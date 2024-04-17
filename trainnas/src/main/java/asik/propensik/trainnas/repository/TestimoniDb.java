package asik.propensik.trainnas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asik.propensik.trainnas.model.Testimoni;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TestimoniDb extends JpaRepository<Testimoni, Long> {

    List<Testimoni> findByNamaContainingIgnoreCase(String nama);


}
