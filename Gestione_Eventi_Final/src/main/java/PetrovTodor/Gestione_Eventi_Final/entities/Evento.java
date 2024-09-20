package PetrovTodor.Gestione_Eventi_Final.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties( { "authorities", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "credentialsNonExpired"})
public class Evento {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idEvento;
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
