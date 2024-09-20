package PetrovTodor.Gestione_Eventi_Final.services;

import PetrovTodor.Gestione_Eventi_Final.entities.Evento;
import PetrovTodor.Gestione_Eventi_Final.entities.User;
import PetrovTodor.Gestione_Eventi_Final.exceptions.NotFoundException;
import PetrovTodor.Gestione_Eventi_Final.payload.EventoDto;
import PetrovTodor.Gestione_Eventi_Final.repositorys.EventoRepository;
import PetrovTodor.Gestione_Eventi_Final.repositorys.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UserRepository userRepository;

    public Evento saveEvento(EventoDto body, String username){;
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("Utente non trovato"));
        Evento evento = new Evento(
                body.titolo(), body.descrizione(), body.dataEvento(), body.numeroPosti(),user);
        return eventoRepository.save(evento);
    }

    public Page<Evento> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventoRepository.findAll(pageable);
    }
    public Evento findById(UUID idEvento) {
        return this.eventoRepository.findById(idEvento).orElseThrow(() -> new NotFoundException(idEvento));
    }
    public void findAndDelete(UUID idEvento) {
        Evento found = this.findById(idEvento);
        this.eventoRepository.delete(found);
    }

    public Evento findAndUpdate(UUID idEvento, EventoDto body) throws BadRequestException {
               Evento found = this.findById(idEvento);
        found.setTitolo(body.titolo());
        found.setDescrizione(body.descrizione());
        found.setDataEvento(body.dataEvento());
        return this.eventoRepository.save(found);
    }

    public List<Evento> findByOrganizzatore(User user) {
        return eventoRepository.findByOrganizzatore(user);
    }
}
