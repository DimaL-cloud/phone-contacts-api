package ua.dmytrolutsyuk.phonecontactsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private String login;

    private String password;
}
