package cl.bci.util;

import java.io.Serializable;

public class JwtDTO implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String email;
    private String password;


    public JwtDTO() {}

    public JwtDTO(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
