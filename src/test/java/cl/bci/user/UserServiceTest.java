package cl.bci.user;

import cl.bci.common.spring.utils.CommonConstants;
import cl.bci.common.spring.utils.ErrorResponse;
import cl.bci.user.domain.entities.UserEntity;
import cl.bci.user.domain.repository.UserRepository;
import cl.bci.user.rest.implementation.UserServiceImpl;
import cl.bci.user.utils.dto.PhoneDTO;
import cl.bci.user.utils.dto.UserDTO;
import cl.bci.user.utils.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(userService, "emailPattern", "^\\S+@dominio\\.cl$");
    }

    @Test
    void testCreateUser_Success() {

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Jose Plaza");
        userDTO.setEmail("joseplaza@dominio.cl");
        userDTO.setPassword("password");

        PhoneDTO phoneDTO = new PhoneDTO("123456789", "1", "56");
        userDTO.setPhones(Collections.singletonList(phoneDTO));

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0);
            savedUser.setIdUserBci("123");
            return savedUser;
        });


        ResponseEntity<?> responseEntity = userService.createUser(userDTO);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(UserResponse.class, responseEntity.getBody());
    }

    @Test
    void testCreateUser_EmailFormatError() {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("invalid.email");

        ResponseEntity<?> responseEntity = userService.createUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertInstanceOf(ErrorResponse.class, responseEntity.getBody());

        assertEquals(CommonConstants.CORREO_FORMATO_ERRONEO, ((ErrorResponse) responseEntity.getBody()).getMensaje());
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("existing.email@dominio.cl");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ResponseEntity<?> responseEntity = userService.createUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertInstanceOf(ErrorResponse.class, responseEntity.getBody());

        assertEquals(CommonConstants.CORREO_REGISTRADO, ((ErrorResponse) responseEntity.getBody()).getMensaje());
    }

    @Test
    void testGetAllUsers_Success() {
        // Given
        UserEntity user1 = new UserEntity();
        user1.setIdUserBci("1");
        user1.setCreated(LocalDateTime.now().minusDays(1));
        user1.setModified(LocalDateTime.now().minusDays(1));
        user1.setLastLogin(LocalDateTime.now().minusDays(1));
        user1.setActive(true);

        UserEntity user2 = new UserEntity();
        user2.setIdUserBci("2");
        user2.setCreated(LocalDateTime.now().minusDays(2));
        user2.setModified(LocalDateTime.now().minusDays(2));
        user2.setLastLogin(LocalDateTime.now().minusDays(2));
        user2.setActive(false);

        List<UserEntity> mockUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<UserResponse> result = userService.getAllUsers();

        assertEquals(2, result.size());

        UserResponse response1 = result.get(0);
        assertEquals("1", response1.getIdUserBci());
        assertEquals(user1.getCreated(), response1.getCreated());
        assertEquals(user1.getModified(), response1.getModified());
        assertEquals(user1.getLastLogin(), response1.getLastLogin());
        assertEquals(user1.isActive(), response1.isActive());

        UserResponse response2 = result.get(1);
        assertEquals("2", response2.getIdUserBci());
        assertEquals(user2.getCreated(), response2.getCreated());
        assertEquals(user2.getModified(), response2.getModified());
        assertEquals(user2.getLastLogin(), response2.getLastLogin());
        assertEquals(user2.isActive(), response2.isActive());
    }
}
