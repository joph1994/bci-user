package cl.bci.controller;

import cl.bci.service.LoginService;
import cl.bci.util.JwtDTO;
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