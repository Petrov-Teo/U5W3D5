package PetrovTodor.Gestione_Eventi_Final.services;

import PetrovTodor.Gestione_Eventi_Final.entities.User;
import PetrovTodor.Gestione_Eventi_Final.exceptions.NotFoundException;
import PetrovTodor.Gestione_Eventi_Final.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(UUID id_user) {
        return this.userRepository.findById(id_user).orElseThrow(() -> new NotFoundException(id_user));
    }
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("l'utente con " + " " + email + " " + "non è stato trovato!"));
    }
}
