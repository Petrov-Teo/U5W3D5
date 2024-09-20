package PetrovTodor.Gestione_Eventi_Final.repositorys;

import PetrovTodor.Gestione_Eventi_Final.entities.Evento;
import PetrovTodor.Gestione_Eventi_Final.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {

        List<Evento> findByOrganizzatore(User organizzatore);
}
