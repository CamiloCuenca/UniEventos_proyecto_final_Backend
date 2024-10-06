package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.JWT.dtoMessage;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;



}
