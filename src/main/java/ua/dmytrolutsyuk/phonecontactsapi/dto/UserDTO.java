package ua.dmytrolutsyuk.phonecontactsapi.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private String login;

    @Email(message = "Email is not valid")
    private String email;

    private String password;
}
