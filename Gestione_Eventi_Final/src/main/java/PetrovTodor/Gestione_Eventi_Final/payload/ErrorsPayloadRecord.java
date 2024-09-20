package PetrovTodor.Gestione_Eventi_Final.payload;

import java.time.LocalDateTime;

public record ErrorsPayloadRecord(String message, LocalDateTime timeStamp) {
}
