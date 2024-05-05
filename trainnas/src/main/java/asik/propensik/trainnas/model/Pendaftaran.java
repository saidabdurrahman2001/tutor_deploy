package asik.propensik.trainnas.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pendaftaran")
public class Pendaftaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPendaftaran;

    @ManyToOne
    @JoinColumn(name = "pelatihan_id", nullable = false)
    private Pelatihan pelatihan;

    private String namaLengkap;
    private String asalSekolah;
    private String email;
    private String noTelepon;

    @Column(columnDefinition = "timestamp", nullable = false)
    private LocalDateTime waktuPembuatan = LocalDateTime.now();

}
