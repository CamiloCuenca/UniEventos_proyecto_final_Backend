package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;



}
