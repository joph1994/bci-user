package cl.bci.constants;

public class CommonConstants {

    private CommonConstants() {
        throw new IllegalStateException("Clase de Constantes.");
    }

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    public static final String USER = "USER";
    public static final String ROLE = "ROLE_";
    public static final String CONTENT_TYPE = "application/json";
    public static final String ERROR_EMAIL_PASS = "Email o contraseña errónea.";
    public static final String USER_NOT_FOUND = "Usuario con email no encontrado : ";
    public static final String SERVER_ERROR = "Error interno del servidor : ";
    public static final String TOKEN_EXPIRED = "El JWT Token ha expirado.";
    public static final String BAD_TOKEN = "No se puede leer el token.";
    public static final String USUARIO_SIN_PERMISOS = "Usuario sin los suficientes permisos.";
    public static final String CORREO_FORMATO_ERRONEO = "El correo electrónico no cumple con el formato requerido.";
    public static final String CORREO_REGISTRADO = "El correo ya se encuentra registrado.";
    public static final String NOT_AUTHORIZED = "No autorizado.";
    public static final String TOKEN_ERROR = "El token no es igual al token almacenado.";
    public static final String ERROR_REGISTRO_BD = "Ocurrio un error a la hora de registrar el usuario en base de datos : ";
}
