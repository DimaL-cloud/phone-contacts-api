package ua.dmytrolutsyuk.phonecontactsapi.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "Login is mandatory")
    private String login;

    @NotNull(message = "Password is mandatory")
    private String password;
}
