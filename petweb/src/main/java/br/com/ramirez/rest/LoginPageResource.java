package br.com.ramirez.rest;

import br.com.ramirez.domain.model.User;
import br.com.ramirez.domain.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Path("/login")
public class LoginPageResource {

    @Inject
    UserRepository userRepository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public static Response redirectToLoginPage() {
        try {
            // Lendo o conteúdo do arquivo index.html
            String caminhoArquivo = "src/main/resources/META-INF/resources/login.html";
            byte[] content = Files.readAllBytes(Paths.get(caminhoArquivo));
            // Retornando o conteúdo como resposta
            return Response.ok(content).build();
        } catch (IOException e) {
            e.printStackTrace();
            // Se houver um erro ao ler o arquivo, retorna uma resposta de erro 500
            return Response.status(Response.Status.NOT_FOUND).entity("Arquivo não Encontrado!").build();
        }
    }
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/select")
    public Response login(Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        // Busca o usuário no banco de dados pelo email
        User user = userRepository.findByEmail(email);

        // Verifica se o usuário existe e se a senha está correta
        if (user != null && user.getPassword().equals(password)) {
            System.out.println(user);
            // Se as credenciais estiverem corretas, redireciona para a próxima página
            return Response.status(Response.Status.OK)
                    .entity(Map.of(
                            "name", user.getName(),
                            "email", user.getEmail(),
                            "password", user.getPassword(),
                            "userId", user.getId(),
                            "message", "Usuário Logado com sucesso",
                            "redirect", "/update"))
                    .build();

        } else {
            // Se as credenciais estiverem incorretas, retorna um status de não autorizado com uma mensagem de erro
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("username", "Credenciais inválidas! Por favor, tente novamente."))
                    .build();
        }
    }
}
