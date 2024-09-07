package co.edu.uniquindio.proyecto.model.Accounts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("User")
public class User {
    @Id
    private String id;
    private String identityDocument;
    private String username;
    private String phoneNumber;
    private String address;


}
