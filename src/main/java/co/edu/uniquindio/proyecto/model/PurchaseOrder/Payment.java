package co.edu.uniquindio.proyecto.model.PurchaseOrder;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
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
