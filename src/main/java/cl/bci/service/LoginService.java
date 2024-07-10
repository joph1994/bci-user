package cl.bci.service;

import cl.bci.util.JwtDTO;
import org.springframework.http.ResponseEntity;

public interface LoginService {

   ResponseEntity<?> login(JwtDTO authRequest);
}
