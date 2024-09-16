package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.repository.CouponRepository;
import co.edu.uniquindio.proyecto.repository.OrderRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponServiceImp implements CouponService {

    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;

    @Override
    public String createCoupon(CouponDTO couponDTO) throws Exception {
        Coupon newCoupon = new Coupon();
        newCoupon.setName(couponDTO.name());
        newCoupon.setCode(couponDTO.code());
        newCoupon.setDiscount(couponDTO.discount());
        newCoupon.setExpirationDate(couponDTO.expirationDate());
        newCoupon.setStatus(couponDTO.status());
        newCoupon.setType(couponDTO.type());

        Coupon createdCoupon = couponRepository.save(newCoupon);
        return createdCoupon.getId();
    }

    @Override
    public boolean validateCoupon(String code) throws Exception {
        Coupon coupon = couponRepository.findByCode(code);
        if(coupon == null || coupon.getStatus() != CouponStatus.DISPONIBLE){
            throw new Exception("El coupon no existe o no esta activo");
        }


        return true;
    }

    @Override
    public double applyCoupon(String code, String orderId) throws Exception {
        // Validar si el cupón es válido o ya expiró
        if (!validateCoupon(code)) {
            throw new Exception("El cupón es inválido o ya expiró");
        }

        // Buscar el cupón por su código
        Coupon coupon = couponRepository.findByCode(code);

        // Verificar el tipo de cupón (UNICO o MULTIPLE)
        if (coupon.getType() == TypeCoupon.UNICO && coupon.getStatus() == CouponStatus.NO_DISPONIBLE) {
            throw new Exception("El cupón ya ha sido utilizado");
        }

        // Buscar la orden por su ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("La orden no existe"));

        // Aplicar el cupón a la orden
        double discount = applyDiscountToOrder(order, coupon);

        // Marcar el cupón como usado si es UNICO
        if (coupon.getType() == TypeCoupon.UNICO) {
            coupon.setStatus(CouponStatus.NO_DISPONIBLE);
            couponRepository.save(coupon);
        }

        // Guardar la orden actualizada
        orderRepository.save(order);

        // Retornar el valor del descuento aplicado
        return discount;
    }

    @Override
    public List<Coupon> getAvailableCoupons() {
        return couponRepository.findAvailableCoupons();
    }

    @Override
    public void deactivateCoupon(String couponId) throws Exception {
        // Buscar el cupón por su ID y lanzar una excepción si no existe
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new Exception("El cupón no existe"));

        // Cambiar el estado del cupón a INACTIVO
        coupon.setStatus(CouponStatus.NO_DISPONIBLE);

        // Guardar el cupón actualizado en la base de datos
        couponRepository.save(coupon);
    }


    @Override
    public void updateCoupon(String couponId, CouponDTO couponDTO) throws Exception {
        Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);

        if (optionalCoupon.isPresent()) {
            throw new Exception("El coupon no existe");
        }

        Coupon coupon = optionalCoupon.get();
        coupon.setName(couponDTO.name());
        coupon.setCode(couponDTO.code());
        coupon.setDiscount(couponDTO.discount());
        coupon.setExpirationDate(couponDTO.expirationDate());
        coupon.setStatus(couponDTO.status());

        couponRepository.save(coupon);
    }

    private double applyDiscountToOrder(Order order, Coupon coupon) {
        // Lógica para calcular el descuento basado en el tipo de cupón
        double  discountPercentage = Integer.parseInt(coupon.getDiscount())/ 100.0;

        // Calcular el monto del descuento
        double discountAmount = order.getTotal() * discountPercentage;

        // Actualizar el total de la orden
        order.setTotal(order.getTotal() - discountAmount);


        return discountAmount;
    }

}
