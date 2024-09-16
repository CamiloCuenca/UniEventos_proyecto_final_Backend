package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends MongoRepository<Coupon,String> {

    // Method to find a coupon by its code
    Coupon findByCode(String code);
    @Query("{ 'status': 'DISPONIBLE' }")
    List<Coupon> findAvailableCoupons();
}
