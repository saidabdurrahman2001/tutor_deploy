package asik.propensik.trainnas.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateSilabusRequestDTO {
    @NotBlank
    private String tingkatan;
    @NotBlank
    private String deskripsi;
    @NotBlank
    private String filePath;

}
