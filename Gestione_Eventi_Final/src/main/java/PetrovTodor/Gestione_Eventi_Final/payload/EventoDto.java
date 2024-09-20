package PetrovTodor.Gestione_Eventi_Final.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventoDto(
        @NotEmpty(message = "il campo titolo è obbligatorio")
        @Size(min = 5,max = 35)
        String titolo,
        @NotEmpty(message = "il campo descrizione è obbligatorio")
        @Size(min = 25,max = 255)
        String descrizione,
        @NotEmpty(message = "il campo Data Evento è obbligatorio")
        LocalDate dataEvento,
        @NotNull(message = "il campo titolo è obbligatorio")
        int numeroPosti) {
}
