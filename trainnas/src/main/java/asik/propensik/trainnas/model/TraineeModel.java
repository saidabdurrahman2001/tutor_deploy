// package asik.propensik.trainnas.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Lob;
// import jakarta.persistence.Table;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Size;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Setter
// @Getter
// @AllArgsConstructor
// @NoArgsConstructor
// @Entity
// @Table(name = "trainee")
// public class TraineeModel{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long idUser;

//    @NotNull
//    @Size(max = 50)
//    @Column(name = "nama", nullable = false)
//    private String nama;

//    @NotNull
//    @Size(max = 50)
//    @Column(name = "email", nullable = false, unique = true)
//    private String email;

//    @NotNull
//    @Size(max = 50)
//    @Column(name = "nomorTelepon", nullable = false)
//    private String nomorTelepon;

//    @NotNull
//    @Size(max = 50)
//    @Column(name = "asalSekolah", nullable = false)
//    private String asalSekolah;

//    @NotNull
//    @Lob
//    @Column(name = "password", nullable = false)
//    private String password;

//    @NotNull
//    @Size(max = 50)
//    @Column(name = "role", nullable = false)
//    private String role;
// }
