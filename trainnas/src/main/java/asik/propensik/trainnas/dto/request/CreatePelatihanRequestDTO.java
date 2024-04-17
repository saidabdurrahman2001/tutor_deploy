package asik.propensik.trainnas.dto.request;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePelatihanRequestDTO {
    @NotBlank
    private String namaPelatihan;
    @NotBlank
    private String deskripsi;
    @NotBlank
    private String tempat;
    @NotBlank
    private Date tanggal;
    @NotBlank
    private String narahubung;
    @NotNull
    private String tipe;

}
