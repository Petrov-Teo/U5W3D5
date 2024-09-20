package PetrovTodor.Gestione_Eventi_Final.services;

import PetrovTodor.Gestione_Eventi_Final.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
}
