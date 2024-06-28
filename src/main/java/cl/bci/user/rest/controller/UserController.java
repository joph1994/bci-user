package cl.bci.user.rest.controller;

import cl.bci.user.rest.service.UserService;
import cl.bci.user.utils.dto.UserDTO;
import cl.bci.user.utils.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bci/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear un nuevo usuario.", description = "De acuerdo al request creara un usuario solo si el token enviado es correcto y el " +
            "usuario es de tipo ADMIN luego devuelve el usuario creado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Obtener todos los usuarios.", description = "Devuelve la lista de usuarios solo si el token es correcto y el usuario es de " +
            "tipo admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllUsers")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }
}
