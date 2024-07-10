package cl.bci.service;

import cl.bci.util.UserDTO;
import cl.bci.util.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public ResponseEntity<?> createUser(UserDTO user);
    public List<UserResponse> getAllUsers();

}
