package br.com.ramirez.DTO;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ResponseError {
    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    public static final Integer CONFLICT_STATUS = 409;

    private String message;
    private Collection<FieldError> errors;

    public ResponseError(){}

    public ResponseError(String message, Collection <FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ResponseError(String message, List<FieldError> errors, Integer CONFLICT_STATUS) {
        this.message = message;
        this.errors = errors;
    }

    public static ResponseError createFromValidation(Set<ConstraintViolation<UserRequest>> violations){
        List<FieldError> errors = violations
                .stream()
                .map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        String message = "Validation Error";

        return new ResponseError(message, errors);
    }
    public static ResponseError responseErrorEmail() {
        List<FieldError> errors = List.of(new FieldError("email", "E-mail já cadastrado"));
        String message = "Validation Error";


        return new ResponseError(message, errors, CONFLICT_STATUS);
    }

    public Response withStatusCode(Integer code){
        return Response.status(code).entity(this).build();
    }
}
