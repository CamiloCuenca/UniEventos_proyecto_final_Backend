package co.edu.uniquindio.proyecto.model.Accounts;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document("User")
public class User {

    @Id
    private String cedula;
    private String nombre;
    private String telefono;
    private String direccion;


}
