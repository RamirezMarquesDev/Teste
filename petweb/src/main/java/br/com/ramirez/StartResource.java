package br.com.ramirez;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/start")
public class StartResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public static Response redirectToExamplePage() {
        try {
            // Lendo o conteúdo do arquivo index.html
            byte[] content = Files.readAllBytes(Paths.get("src/main/resources/META-INF/resources/example.html"));
            // Retornando o conteúdo como resposta
            return Response.ok(content).build();
        } catch (IOException e) {
            e.printStackTrace();
            // Se houver um erro ao ler o arquivo, retorna uma resposta de erro 500
            return Response.status(Response.Status.NOT_FOUND).entity("Arquivo não Encontrado!").build();
        }
    }
}

