package PetrovTodor.Gestione_Eventi_Final.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserLoginDto(
        @NotEmpty(message = "il Campo è obbligatorio!")
        @Email
        String email,
        @NotEmpty(message = "il Campo è obbligatorio! Minimo 2 caratteri")
        String password) {

}
