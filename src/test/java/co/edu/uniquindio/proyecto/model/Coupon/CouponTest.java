package co.edu.uniquindio.proyecto.model.Coupon;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CouponTest {

    @Autowired
    private CouponService couponService;

    @Test
    public void createCouponTest() throws Exception {
        CouponDTO  couponDTO = new CouponDTO("Amor y la Amistad","12345","10", LocalDateTime.now(), CouponStatus.DISPONIBLE, TypeCoupon.MULTIPLE);
        couponService.createCoupon(couponDTO);

    }
}
