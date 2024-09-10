package br.com.ramirez.DTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@ApplicationScoped
public class UserRequest {

    @NotBlank(message = "Nome é Obrigatório!")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 4 e 100 caracteres!")
    private String name;

    @NotBlank(message = "Email é Obrigatório!")
    @Email(message = "Favor inserir um e-mail válido!")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 4, max = 8, message = "A senha deve conter entre 4 e 8 caracteres!")
    private String password;

    public Long getId() {
        return null;
    }
}
