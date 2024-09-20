package PetrovTodor.Gestione_Eventi_Final.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserDto(
        @NotEmpty(message = "il Campo è obbligatorio! Minimo 2 caratteri")
        @Size(min = 2)
        String nome,
        @NotEmpty(message = "il Campo è obbligatorio! Minimo 2 caratteri")
        @Size(min = 2)
        String cognome,
        @NotNull(message = "il campo data di nascita è obbligatorio")
        LocalDate dataDiNascita,
        @NotEmpty(message = "il Campo è obbligatorio!")
        @Email
        String email,
        @NotEmpty(message = "il Campo è obbligatorio! Minimo 2 caratteri")
        String password
        ) {
}
