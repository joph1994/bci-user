package cl.bci.repository;

import javax.persistence.*;


@Entity
@Table(name = "USER_PHONE")
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phone")
    private Long idPhone;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String citycode;

    @Column(nullable = false)
    private String contrycode;

    @ManyToOne
    @JoinColumn(name = "id_user_bci", nullable = false)
    private UserEntity userEntity;

    // Getters y Setters

    public Long getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(Long idPhone) {
        this.idPhone = idPhone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getContrycode() {
        return contrycode;
    }

    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }

    public UserEntity getUserBci() {
        return userEntity;
    }

    public void setUserBci(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
