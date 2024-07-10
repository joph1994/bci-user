package cl.bci.service.implementation;

import cl.bci.constants.CommonConstants;
import cl.bci.repository.PhoneEntity;
import cl.bci.repository.UserEntity;
import cl.bci.repository.UserRepository;
import cl.bci.service.UserService;
import cl.bci.util.PhoneDTO;
import cl.bci.util.UserDTO;
import cl.bci.util.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Value("${email.Pattern}")
    private Pattern emailPattern;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> createUser(UserDTO userDTO) {

        if (!Pattern.matches(String.valueOf(emailPattern), userDTO.getEmail())) {
            throw new IllegalArgumentException(CommonConstants.CORREO_FORMATO_ERRONEO);
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException(CommonConstants.CORREO_REGISTRADO);
        }

        UserEntity userBci = new UserEntity();
        userBci.setIdUserBci(UUID.randomUUID().toString());
        userBci.setName(userDTO.getName());
        userBci.setEmail(userDTO.getEmail());
        userBci.setPassword(userDTO.getPassword());
        userBci.setCreated(LocalDateTime.now());
        userBci.setModified(LocalDateTime.now());
        userBci.setLastLogin(LocalDateTime.now());
        userBci.setActive(true);
        userBci.setType(CommonConstants.USER);


        List<PhoneEntity> userPhones = new ArrayList<>();
        if (userDTO.getPhones() != null) {
            for (PhoneDTO phoneDTO : userDTO.getPhones()) {
                PhoneEntity userPhone = new PhoneEntity();
                userPhone.setNumber(phoneDTO.getNumber());
                userPhone.setCitycode(phoneDTO.getCitycode());
                userPhone.setContrycode(phoneDTO.getContrycode());
                userPhone.setUserBci(userBci);
                userPhones.add(userPhone);
            }
        }

        userBci.setUserPhones(userPhones);
        try {
            UserEntity user = userRepository.save(userBci);
            UserResponse userResponse = new UserResponse();

            userResponse.setIdUserBci(user.getIdUserBci());
            userResponse.setCreated(user.getCreated());
            userResponse.setModified(user.getModified());
            userResponse.setLastLogin(user.getLastLogin());
            userResponse.setActive(user.isActive());


            return ResponseEntity.ok(userResponse);
        }catch (Exception e){
            throw new RuntimeException(CommonConstants.ERROR_REGISTRO_BD + e.getMessage());
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {

        List<UserEntity> allUsers = userRepository.findAll();

        List<UserResponse> listUserResponse = new ArrayList<>();

        for (UserEntity user : allUsers) {
            UserResponse userResponse = new UserResponse();

            userResponse.setIdUserBci(user.getIdUserBci());
            userResponse.setCreated(user.getCreated());
            userResponse.setModified(user.getModified());
            userResponse.setLastLogin(user.getLastLogin());
            userResponse.setActive(user.isActive());

            listUserResponse.add(userResponse);
        }

        return listUserResponse;
    }


}
