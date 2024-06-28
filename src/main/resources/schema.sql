CREATE TABLE USER_BCI (
                          id_user_bci VARCHAR(36) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          created TIMESTAMP NOT NULL,
                          modified TIMESTAMP NOT NULL,
                          last_login TIMESTAMP NOT NULL,
                          is_active BOOLEAN NOT NULL,
                          type VARCHAR(255) NOT NULL
);

CREATE TABLE USER_PHONE (
                            id_phone BIGINT PRIMARY KEY AUTO_INCREMENT,
                            number VARCHAR(20) NOT NULL,
                            citycode VARCHAR(10) NOT NULL,
                            contrycode VARCHAR(10) NOT NULL,
                            id_user_bci VARCHAR(36) NOT NULL,
                            FOREIGN KEY (id_user_bci) REFERENCES USER_BCI(id_user_bci)
);
