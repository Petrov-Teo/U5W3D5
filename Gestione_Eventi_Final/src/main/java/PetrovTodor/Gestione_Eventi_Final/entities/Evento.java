package PetrovTodor.Gestione_Eventi_Final.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "eventi")
@Entity
public class Evento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id_evento;
    private String titolo;
    private String descrizione;
    private LocalDate dataEvento;
    private int numeroPosti;


    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    private User organizzatore;

    public Evento(String titolo, String descrizione, LocalDate dataEvento, int numeroPosti, User organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataEvento = dataEvento;
        this.numeroPosti = numeroPosti;
        this.organizzatore = organizzatore;
    }


}
