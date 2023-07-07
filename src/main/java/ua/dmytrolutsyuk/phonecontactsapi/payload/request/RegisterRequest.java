package ua.dmytrolutsyuk.phonecontactsapi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "Login is mandatory")
    private String login;

    @Email(message = "Email is not valid")
    @NotNull(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Password is mandatory")
    private String password;
}
