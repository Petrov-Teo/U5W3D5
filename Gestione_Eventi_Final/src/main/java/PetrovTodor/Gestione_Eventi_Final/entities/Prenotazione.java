package PetrovTodor.Gestione_Eventi_Final.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id_prenotazione;

    @ManyToOne
    private User user;

    @ManyToOne
    private Evento event;

}
