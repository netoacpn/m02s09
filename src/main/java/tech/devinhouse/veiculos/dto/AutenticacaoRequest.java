package tech.devinhouse.veiculos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AutenticacaoRequest {

    @NotEmpty(message = "{required.field}")
    @Email(message = "{invalid.field}")
    private String email;

    @NotEmpty(message = "{required.field}")
    private String senha;

}
