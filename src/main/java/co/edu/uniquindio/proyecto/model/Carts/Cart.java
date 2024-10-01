package co.edu.uniquindio.proyecto.model.Carts;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document ("Cart")
public class Cart {

    @Id
    private String id;
    private LocalDateTime date;
    private List<CartDetail> items;


}
