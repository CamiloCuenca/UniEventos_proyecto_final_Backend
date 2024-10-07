package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface CouponRepository extends MongoRepository<Coupon,String> {

    // Method to find a coupon by its code
    Coupon findByCode(String code);
    @Query("{ 'status': 'AVAILABLE' }")
    List<Coupon> findAvailableCoupons();


}
