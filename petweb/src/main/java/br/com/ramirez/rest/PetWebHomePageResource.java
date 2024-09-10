package br.com.ramirez.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/home")
public class PetWebHomePageResource {
    @GET
    public static Response redirectToExamplePage() {
        try {
            byte[] content = Files.readAllBytes(Paths.get("src/main/resources/META-INF/resources/index.html"));
            return Response.ok(content).build();
        } catch (IOException e) {
            e.printStackTrace();
            // Se houver um erro ao ler o arquivo, retorna uma resposta de erro 400
            return Response.status(Response.Status.NOT_FOUND).entity("Arquivo não Encontrado!").build();
        }
    }
    @POST
    @Path("/home")
    @Produces(MediaType.TEXT_HTML)
    public Response redirectToExamplePagePost() {
        return redirectToExamplePage(); // Reutilizando a lógica de redirectToExamplePage
    }
}
