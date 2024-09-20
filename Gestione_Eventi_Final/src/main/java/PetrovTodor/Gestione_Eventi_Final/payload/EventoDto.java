package PetrovTodor.Gestione_Eventi_Final.payload;

import PetrovTodor.Gestione_Eventi_Final.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record EventoDto(
        @NotEmpty(message = "il campo titolo è obbligatorio")
        String titolo,
        @NotEmpty(message = "il campo descrizione è obbligatorio")

        String descrizione,
        @NotNull(message = "il campo data è obbligatorio")
        LocalDate dataEvento,
        @NotNull(message = "il campo numero posti è obbligatorio")
        int numeroPosti)
       {
}
