package PetrovTodor.Gestione_Eventi_Final.repositorys;

import PetrovTodor.Gestione_Eventi_Final.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
}
