package PetrovTodor.Gestione_Eventi_Final.services;
import PetrovTodor.Gestione_Eventi_Final.entities.Prenotazione;
import PetrovTodor.Gestione_Eventi_Final.exceptions.NotFoundException;
import PetrovTodor.Gestione_Eventi_Final.payload.responsPayload.PrenotazioneDto;
import PetrovTodor.Gestione_Eventi_Final.repositorys.PrenotazioneRepository;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public Prenotazione saveEvento(PrenotazioneDto body){
        Prenotazione prenotazione = new Prenotazione(body.id_user(),body.id_evento());
        return prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }
    public Prenotazione findById(UUID idPrenotazione) {
        return this.prenotazioneRepository.findById(idPrenotazione).orElseThrow(() -> new NotFoundException(idPrenotazione));
    }
    public void findAndDelete(UUID idPrenotazione) {
        Prenotazione found = this.findById(idPrenotazione);
        this.prenotazioneRepository.delete(found);
    }

    public Prenotazione findAndUpdate(UUID idPrenotazione, PrenotazioneDto body) throws BadRequestException {
        Prenotazione found = this.findById(idPrenotazione);
        found.setId_user(body.id_user());
        found.setId_evento(body.id_evento());
        return this.prenotazioneRepository.save(found);
    }


}
