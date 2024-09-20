package PetrovTodor.Gestione_Eventi_Final.payload.responsPayload;

import PetrovTodor.Gestione_Eventi_Final.entities.Evento;
import PetrovTodor.Gestione_Eventi_Final.entities.User;
import jakarta.validation.constraints.NotNull;

public record PrenotazioneDto(
        @NotNull(message = "campo id_user è obbligatorio!")
        User id_user,
        @NotNull(message = "campo id_user è obbligatorio!")
        Evento id_evento ) {
}
