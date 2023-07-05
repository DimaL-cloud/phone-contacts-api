package ua.dmytrolutsyuk.phonecontactsapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.Data;
import ua.dmytrolutsyuk.phonecontactsapi.validation.PhoneNumber;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ContactDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Emails cannot be null")
    @Size(min = 1, message = "At least one email is required")
    private List<@Email(message = "Email is not valid") String> emails;

    @NotNull(message = "Phone numbers cannot be null")
    @Size(min = 1, message = "At least one phone number is required")
    private List<@PhoneNumber String> phoneNumbers;
}
