package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouponRepository extends MongoRepository<Coupon,String> {

}
