package cl.bci.user

import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification
import java.util.regex.Pattern
import cl.bci.repository.UserRepository
import cl.bci.service.implementation.UserServiceImpl
import cl.bci.util.UserDTO
import cl.bci.util.PhoneDTO
import cl.bci.util.UserResponse
import cl.bci.repository.UserEntity
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

class Test extends Specification {

    def "Test crear usuario exitoso"() {
        given:
        def userRepository = Mock(UserRepository)
        def userService = new UserServiceImpl(userRepository)
        def patternString = '^\\S+@dominio\\.cl$'
        def pattern = Pattern.compile(patternString)
        ReflectionTestUtils.setField(userService, 'emailPattern', pattern)

        def userDTO = new UserDTO(
                name: "Usuario Test",
                email: "usuario@dominio.cl",
                password: "password",
                phones: [
                        new PhoneDTO("12345678", "1", "56")
                ]
        )

        userRepository.existsByEmail(_) >> false
        userRepository.save(_) >> new UserEntity(idUserBci: UUID.randomUUID().toString())

        when:
        ResponseEntity<?> response = userService.createUser(userDTO)

        then:
        response.statusCode == HttpStatus.OK
        response.body instanceof UserResponse
    }

    def "Test crear usuario con correo inválido"() {
        given:
        def userRepository = Mock(UserRepository)
        def userService = new UserServiceImpl(userRepository)
        def patternString = '^\\S+@dominio\\.cl$'
        def pattern = Pattern.compile(patternString)
        ReflectionTestUtils.setField(userService, 'emailPattern', pattern)

        def userDTO = new UserDTO(
                name: "Usuario Test",
                email: "usuario@test.cl",
                password: "password",
                phones: [
                        new PhoneDTO("12345678", "1", "56")
                ]
        )

        when:
        userService.createUser(userDTO)

        then:
        thrown IllegalArgumentException
    }

    def "Test crear usuario con correo ya registrado"() {
        given:
        def userRepository = Mock(UserRepository)
        def userService = new UserServiceImpl(userRepository)
        def patternString = '^\\S+@dominio\\.cl$'
        def pattern = Pattern.compile(patternString)
        ReflectionTestUtils.setField(userService, 'emailPattern', pattern)

        def userDTO = new UserDTO(
                name: "Usuario Test",
                email: "usuario@test.cl",
                password: "password",
                phones: [
                        new PhoneDTO("12345678", "1", "56")
                ]
        )

        userRepository.existsByEmail(_) >> true

        when:
        userService.createUser(userDTO)

        then:
        thrown IllegalArgumentException
    }

    def "Test obtener todos los usuarios"() {
        given:
        def userRepository = Mock(UserRepository)
        def userService = new UserServiceImpl(userRepository)
        def patternString = '^\\S+@dominio\\.cl$'
        def pattern = Pattern.compile(patternString)
        ReflectionTestUtils.setField(userService, 'emailPattern', pattern)

        def usersInDb = [
                new UserEntity(idUserBci: UUID.randomUUID().toString(), name: "Usuario 1"),
                new UserEntity(idUserBci: UUID.randomUUID().toString(), name: "Usuario 2")
        ]
        userRepository.findAll() >> usersInDb

        when:
        List<UserResponse> users = userService.getAllUsers()

        then:
        users.size() == 2
        users.every { it instanceof UserResponse }
    }

    def "Test manejo de excepciones al crear usuario"() {
        given:
        def userRepository = Mock(UserRepository)
        def userService = new UserServiceImpl(userRepository)
        def patternString = '^\\S+@dominio\\.cl$'
        def pattern = Pattern.compile(patternString)
        ReflectionTestUtils.setField(userService, 'emailPattern', pattern)

        def userDTO = new UserDTO(
                name: "Usuario Test",
                email: "usuario@test.cl",
                password: "password",
                phones: [
                        new PhoneDTO("12345678", "1", "56")
                ]
        )

        userRepository.existsByEmail(_) >> false
        userRepository.save(_) >> { throw new RuntimeException("Error de base de datos") }

        when:
        userService.createUser(userDTO)

        then:
        thrown RuntimeException
    }
}
