# bci-user

Diagrama Inicial.
![Diagrama](https://github.com/joph1994/bci-user/assets/52262431/3392a22a-84b9-4a74-a6ba-46c2a8edc154)


Pasos para probar:

  1 - Descargar proyecto y abrirlo con su IDE preferido.
  2 - Ejecutar el proyecto.
  3 - Verificar que los scrips iniciales se cargaron correctamente, para ello vamos a http://localhost:8080/h2-console y confifuramos los siguientes valores :
      - Driver Class:org.h2.Driver.
      - JDBC URL:	jdbc:h2:mem:bcidb
      - User Name: sa
      - Password: 
      Ejecutamos test conection y si todo sale bien procedemos a ejcutar connect.
      ![image](https://github.com/joph1994/bci-user/assets/52262431/3891f377-3b6d-4c7d-896e-b97a0e6ee570)
      Luego en la consola ejecutamos las siguientes sentencias : 
        - SELECT * FROM USER_BCI ;
        - SELECT * FROM USER_PHONE;
        ![image](https://github.com/joph1994/bci-user/assets/52262431/62a35678-172e-4c36-974b-6461f8c096c8)

  4 - Validamos en nuestro IDE preferido que las pruebas unitarias esten correctas.

    ![image](https://github.com/joph1994/bci-user/assets/52262431/8ac4f29e-7b56-4d07-a66c-c2e8ff405dd9)

  5 - Nos dirigimos a http://localhost:8080/swagger-ui/index.html#/
    - Ejecutamos el endpoint Login con la siguiente estructura : 
    ```
      {
        "email": "admin@dominio.cl",
        "password": "admin"
      }
    ```
      Nos devolvera un token el cual debemos colocar en el swagger para ejecutar los siguientes metodos :
      ![image](https://github.com/joph1994/bci-user/assets/52262431/bbc3b847-7f8a-4999-bbca-f6e8e7a3730c)

  6 - Colocolar el token en el candado :
    ![image](https://github.com/joph1994/bci-user/assets/52262431/8f4bfb5f-6559-44c1-bba3-3bf5c75fc0b8)
    ![image](https://github.com/joph1994/bci-user/assets/52262431/9a29d848-40cb-43fe-a349-b195724b6017)

  7 - Ejecutar el endpoint de crear usuario con la siguiente estructura :
  ```
          {
              "name": "Juan Test",
              "email": "juantest@dominio.cl",
              "password": "juan",
              "phones": [
              {
                      "number": "12345674",
                      "citycode": "1",
                      "contrycode": "57"
                  },
                  {
                      "number": "76543251",
                      "citycode": "2",
                      "contrycode": "57"
                  }
              ]
          }
  ```

