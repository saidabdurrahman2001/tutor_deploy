package asik.propensik.trainnas.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "testimoni")
public class Testimoni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTestimoni;

    private String komentar;
    private String nama;
    private Date tanggal;

}
