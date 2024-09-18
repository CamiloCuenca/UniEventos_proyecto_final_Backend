package co.edu.uniquindio.proyecto.model.Accounts;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import co.edu.uniquindio.proyecto.Enum.Rol;
import co.edu.uniquindio.proyecto.model.Accounts.ValidationCode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document("Cuenta")
public class Account {

    @Id
    private String  accountId;
    private String email;
    private ValidationCode registrationValidationCode;
    private Rol rol;
    private LocalDateTime registrationDate;
    private String password;
    private AccountStatus status;
    private ValidationCode passwordValidationCode;
    private User user;

}