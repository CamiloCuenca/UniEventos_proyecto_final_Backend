package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.Account.dtoAccountInformation;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account")
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/obtener-info/{id}")
    public dtoAccountInformation obtainAccountInformation(@PathVariable String id) throws Exception {
        return accountService.obtainAccountInformation(id);
    }
}
