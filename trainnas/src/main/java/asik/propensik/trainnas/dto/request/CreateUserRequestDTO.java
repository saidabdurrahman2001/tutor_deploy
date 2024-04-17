package asik.propensik.trainnas.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDTO {
   @NotBlank
   private String name;
   @NotBlank
   private String email;
   @NotBlank
   private String phoneNumber;
   @NotBlank
   private String school;
   @NotBlank
   private String password;
   @NotBlank
   private String role;
}
