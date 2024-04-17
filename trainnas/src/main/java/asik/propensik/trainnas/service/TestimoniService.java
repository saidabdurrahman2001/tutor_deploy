package asik.propensik.trainnas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asik.propensik.trainnas.model.Testimoni;
import asik.propensik.trainnas.repository.TestimoniDb;

@Service
public class TestimoniService {

    @Autowired
    TestimoniDb testimoniDb;

    public List<Testimoni> getAllTestimoni() {
        return testimoniDb.findAll();
    }

    public void addTestimoni(Testimoni testimoni) {
        testimoniDb.save(testimoni);
    }

    public void deleteTestimoni(Long id) {
        Testimoni testimoni = testimoniDb.findById(id).get();
        testimoniDb.delete(testimoni);
    }

    public List<Testimoni> searchTestimoni(String search) {
        return testimoniDb.findByNamaContainingIgnoreCase(search);
    }

}
