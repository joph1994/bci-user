package cl.bci.user.rest.service;

import cl.bci.user.utils.dto.UserDTO;
import cl.bci.user.utils.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public ResponseEntity<?> createUser(UserDTO user);
    public List<UserResponse> getAllUsers();

}
