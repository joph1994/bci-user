package cl.bci.user.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER_BCI")
public class UserEntity {

    @Id
    @Column(name = "id_user_bci", length = 36)
    private String idUserBci;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime modified;

    @Column(name = "last_login", nullable = false)
    private LocalDateTime lastLogin;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PhoneEntity> userPhones;

    @Column(name = "type", nullable = false)
    private String type;

    // Getters y Setters

    public String getIdUserBci() {
        return idUserBci;
    }

    public void setIdUserBci(String idUserBci) {
        this.idUserBci = idUserBci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<PhoneEntity> getUserPhones() {
        return userPhones;
    }

    public void setUserPhones(List<PhoneEntity> userPhones) {
        this.userPhones = userPhones;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}