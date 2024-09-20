package PetrovTodor.Gestione_Eventi_Final.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;
@Getter
@Setter
@Entity
@ToString
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id_prenotazione;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento id_evento;

    public Prenotazione(User user, Evento event) {
        this.id_user = user;
        this.id_evento = event;
    }
}
