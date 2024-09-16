package co.edu.uniquindio.proyecto.model.Coupon;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CouponTest {

    @Autowired
    private CouponService couponService;

    @Test
    public void createCouponTest() throws Exception {
        CouponDTO  couponDTO = new CouponDTO("Amor y la Amistad 3","123","10", LocalDateTime.now(), CouponStatus.NO_DISPONIBLE, TypeCoupon.MULTIPLE);
        couponService.createCoupon(couponDTO);

    }

    @Test
    public void validateCouponTest() throws Exception {
        String code = "12345";
        couponService.validateCoupon(code);
    }

    @Test
    public void getAvailableCouponsTest() throws Exception {
        List<Coupon> couponList = couponService.getAvailableCoupons();
        if (couponList.isEmpty()){
            throw new Exception("No hay cupones validos");
        }

        System.out.println(Arrays.toString(couponList.toArray()));
    }

    @Test
    public void deactivateCouponTest() throws Exception {
        String couponId= "66e8865bdbc7b554d5bbc780";
        couponService.deactivateCoupon(couponId);
    }

}
