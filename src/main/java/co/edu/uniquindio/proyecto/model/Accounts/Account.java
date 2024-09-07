package co.edu.uniquindio.proyecto.model.Accounts;

import co.edu.uniquindio.proyecto.Enum.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("Cuenta")
public class Account {

    @Id
    private String id;
    private String email;
    private ValidationCode registrationValidationCode;
    private ObjectId idUser;
    private LocalDateTime registrationDate;
    private String password;
    private AccountStatus status;
    private ValidationCode passwordValidationCode;

}
