package ua.dmytrolutsyuk.phonecontactsapi.payload.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ua.dmytrolutsyuk.phonecontactsapi.validation.PhoneNumber;

import java.util.Set;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ContactRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Emails cannot be null")
    @Size(min = 1, message = "At least one email is required")
    private Set<@Email(message = "Email is not valid") String> emails;

    @NotNull(message = "Phone numbers cannot be null")
    @Size(min = 1, message = "At least one phone number is required")
    private Set<@PhoneNumber String> phoneNumbers;
}
