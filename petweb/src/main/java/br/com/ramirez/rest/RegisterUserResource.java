package br.com.ramirez.rest;


import br.com.ramirez.DTO.ResponseError;
import br.com.ramirez.DTO.UserRequest;
import br.com.ramirez.domain.model.User;
import br.com.ramirez.domain.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@Path("/cadastrar")
@Data
public class RegisterUserResource {

    private final UserRepository userRepository;
    private final Validator validator;

    @Inject
    public RegisterUserResource(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public static Response redirectToCadastrarPage() {
        try {
            byte[] content = Files.readAllBytes(Paths.get("src/main/resources/META-INF/resources/cadastrar.html"));
            // Retornando o conteúdo como resposta
            return Response.ok(content).build();
        } catch (IOException e) {
            e.printStackTrace();
            // Se houver um erro ao ler o arquivo, retorna uma resposta de erro 400
            return Response.status(Response.Status.NOT_FOUND).entity("Arquivo não Encontrado!").build();
        }
    }
    @POST
    @Transactional
    @Path("/persist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserRequest userRequest){

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        if(!violations.isEmpty()){
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }else {
            User existingUser = userRepository.find("email", userRequest.getEmail()).firstResult();
            if (existingUser != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Usuário já cadastrado, favor faça login.")
                        .build();
            }

            User user = new User();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            userRepository.persist(user);

            return Response.status(Response.Status.CREATED).entity(user).header("Location", "/login").build();
        }
    }
}
