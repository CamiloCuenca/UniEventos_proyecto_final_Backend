package co.edu.uniquindio.proyecto.model.PurchaseOrder;

import co.edu.uniquindio.proyecto.Enum.PaymentType;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
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
    private PaymentType typePayment;
    private String authorizationCode;
    private LocalDateTime date;
    private double transactionValue;
    private PaymentState state;
}
