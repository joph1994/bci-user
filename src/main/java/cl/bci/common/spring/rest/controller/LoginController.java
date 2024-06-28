package cl.bci.common.spring.rest.controller;

import cl.bci.common.spring.rest.service.LoginService;
import cl.bci.common.spring.utils.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {


    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtDTO authRequest) throws BadCredentialsException {
        return loginService.login(authRequest);
    }
}