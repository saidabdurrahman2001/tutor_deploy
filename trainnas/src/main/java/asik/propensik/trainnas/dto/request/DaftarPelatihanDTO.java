package asik.propensik.trainnas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaftarPelatihanDTO {
    @NotNull
    private Long pelatihanId;

    @NotBlank
    private String namaLengkap;

    @NotBlank
    private String asalSekolah;

    @NotBlank
    private String email;

    @NotBlank
    private String noTelepon;

    public void setPelatihanId(Long pelatihanId) {
        this.pelatihanId = pelatihanId;
    }
}