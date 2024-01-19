package com.spring.security.services.models.validation;


import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.services.models.dtos.ResponseDTO;

public class UserValidation {

    public ResponseDTO validation(UserEntity user) {

        ResponseDTO response = new ResponseDTO(); //Instanciar

        response.setNumOfErrors(0); //Inicializamos por defecto el numero de errores

        //Condicion o validaccion en FirstName
        if (user.getFirstName() == null || user.getFirstName().length() < 3 || user.getFirstName().length() > 15) {
            response.setNumOfErrors(response.getNumOfErrors() + 1); //Con esto anadio un error mas
            response.setMessage("El campo firstName no puede ser nulo, tampoco puede tener menos de 3 caracteres ni mas de 15");
        }

        //Condicion o validaccion en LastName
        if (user.getLastName() == null || user.getLastName().length() < 3 || user.getLastName().length() > 30) {
            response.setNumOfErrors(response.getNumOfErrors() + 1); //Con esto anadio un error mas
            response.setMessage("El campo lastName no puede ser nulo, tampoco puede tener menos de 3 caracteres ni mas de 30");
        }

        //Condicion o validaccion en Email
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            response.setNumOfErrors(response.getNumOfErrors() + 1); //Con esto anadio un error mas
            response.setMessage("El campo email no es correcto");
        }

        //Condicion o validaccion en Password
        if (user.getPassword() == null || !user.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")) {
            response.setNumOfErrors(response.getNumOfErrors() + 1); //Con esto anadio un error mas
            response.setMessage("La contraseña debe tener entre 8 y 16 caracteres, al menos un número, una minúscula y una mayúscula.");
        }

        return response;

    }

}
