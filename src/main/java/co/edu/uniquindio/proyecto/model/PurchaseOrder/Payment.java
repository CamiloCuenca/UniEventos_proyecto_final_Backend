package co.edu.uniquindio.proyecto.model.PurchaseOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Payment {

    private String id;
    private String currency;
    private String typePayment;
    private String authorizationCode;
    private LocalDateTime date;
    private double transactionValue;
    private String state;

}
