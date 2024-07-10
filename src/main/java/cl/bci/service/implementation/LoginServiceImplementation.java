package cl.bci.service.implementation;

import cl.bci.service.LoginService;
import cl.bci.constants.CommonConstants;
import cl.bci.util.JwtDTO;
import cl.bci.util.JwtResponse;
import cl.bci.util.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImplementation implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public LoginServiceImplementation(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public ResponseEntity<?> login(JwtDTO authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

            final String jwt = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(CommonConstants.ERROR_EMAIL_PASS, e);
        } catch (Exception e) {
            throw new RuntimeException(CommonConstants.SERVER_ERROR,e);
        }
    }
}
