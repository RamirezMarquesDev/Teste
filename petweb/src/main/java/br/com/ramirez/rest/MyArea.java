package br.com.ramirez.rest;

import br.com.ramirez.DTO.UserRequest;
import br.com.ramirez.domain.model.User;
import br.com.ramirez.domain.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Path("/update")
public class MyArea {

    @Inject
    UserRepository userRepository;
    @Inject
    Validator validator;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public static Response redirectToMyAreaPage() {
        try {
            String caminhoArquivo = "src/main/resources/META-INF/resources/update.html";
            byte[] content = Files.readAllBytes(Paths.get(caminhoArquivo));
            return Response.ok(content).build();
        } catch (IOException e) {
            e.printStackTrace();
            // Se houver um erro ao ler o arquivo, retorna uma resposta de erro 500
            return Response.status(Response.Status.NOT_FOUND).entity("Arquivo não Encontrado!").build();
        }
    }
    @POST
    @Path("/edit")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UserRequest userRequest) {
        User existingUser = userRepository.findByEmail(userRequest.getEmail());

        if (existingUser != null) {
            // Atualiza os campos do usuário existente
            existingUser.setName(userRequest.getName());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setPassword(userRequest.getPassword());
            userRepository.persist(existingUser);

            URI location = UriBuilder.fromPath("/home").build();

            return Response.ok()
                    .location(location)
                    .entity(Map.of("message", "Usuário atualizado com sucesso"))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Usuário não encontrado"))
                    .build();
        }
    }
    @DELETE
    @Path("/delete/{email}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("email") String email) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            userRepository.delete(existingUser);

            URI location = UriBuilder.fromPath("/home").build();

            return Response.ok()
                    .location(location)
                    .entity(Map.of("message", "Usuário deletado com sucesso"))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "ERRO ao deletar Usuário."))
                    .build();
        }
    }

}
