package asik.propensik.trainnas.model;

import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pelatihan")
@SQLDelete(sql = "UPDATE pelatihan SET is_deleted = true WHERE id_pelatihan=?")
@Where(clause = "is_deleted=false")
public class Pelatihan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPelatihan;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String namaPelatihan;

    @NotNull
    @Column(name = "tipe", nullable = false)
    private String tipe;

    @NotNull
    @Column(name = "tempat", nullable = false)
    private String tempat;

    @NotNull
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @NotNull
    @Column(name = "narahubung", nullable = false)
    private String narahubung;

    @NotNull
    @Column(name = "tanggal_pelatihan", nullable = false)
    private Date tanggal;


    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = Boolean.FALSE;

    @Column(name = "status")
    private int statusApproval = 1; // 1 request, 2 approved, 3 rejected, 4 requested delete

    @Column(name = "komentar")
    private String komentar;

    @Column(columnDefinition = "timestamp", nullable = false)
    private LocalDateTime waktuPembuatan = LocalDateTime.now();
}
