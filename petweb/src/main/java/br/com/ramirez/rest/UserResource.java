package br.com.ramirez.rest;

import br.com.ramirez.DTO.UserRequest;
import br.com.ramirez.domain.model.User;
import br.com.ramirez.domain.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Data
public class UserResource {

    private final UserRepository userRepository;
    private final Validator validator;

    @Inject
    public UserResource(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }
    @GET
    public Response listAllUsers(){
        PanacheQuery<User> query = userRepository.findAll(); // <-- ctrl + alt + v para gerar a variável do tipo Panache
        return Response.ok(query.list()).build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Long id){
        User user = userRepository.findById(id);
        if(user != null){
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Usuário não existe!").build();
    }

    @DELETE
    @Transactional
    @Path("/del/{id}")
    public Response deleteUser(@PathParam("id") Long id){
        User user = userRepository.findById(id);
        if(user != null){
            userRepository.delete(user);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Usuário não existe!").build();
    }
    @PUT
    @Transactional
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, UserRequest userRequest){
        User user = userRepository.findById(id);
        if(user != null){
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Usuário não econtrado!").build();
    }


}
