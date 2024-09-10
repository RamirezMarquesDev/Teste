package br.com.ramirez.rest;

import br.com.ramirez.domain.model.FormData;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import lombok.Data;

@Path("/api")
@Data
public class MailResource {

    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;

    @POST
    @Path("/send-mail")
    @Consumes(MediaType.APPLICATION_JSON)
    @Blocking
    public String sendEmailReactive(FormData formData) {
        String name = formData.getName();
        String email = formData.getEmail();
        String phone = formData.getPhone();
        String message = formData.getMessage();
        // Constrói o conteúdo do e-mail com base nos parâmetros da URL
        String emailContent =
                "Nome: " + name + "\n" +
                "E-mail: " + email + "\n" +
                "Telefone: " + phone + "\n" +
                "Mensagem: " + message;


        // Envia o e-mail usando o método send do ReactiveMailer
        return reactiveMailer.send(Mail.withText("petcomerceweb@gmail.com", "Contato do site PetCommerceWeb", emailContent))
                .map(x -> "E-mail enviado com sucesso!")
                .await().indefinitely();

    }
    }

