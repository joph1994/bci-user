package cl.bci.common.spring.rest.service;

import cl.bci.common.spring.utils.JwtDTO;
import org.springframework.http.ResponseEntity;

public interface LoginService {

   ResponseEntity<?> login(JwtDTO authRequest);
}
