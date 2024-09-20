package PetrovTodor.Gestione_Eventi_Final.services;

import PetrovTodor.Gestione_Eventi_Final.entities.User;
import PetrovTodor.Gestione_Eventi_Final.exceptions.NotFoundException;
import PetrovTodor.Gestione_Eventi_Final.exceptions.UnauthorizedException;
import PetrovTodor.Gestione_Eventi_Final.payload.UserDto;
import PetrovTodor.Gestione_Eventi_Final.repositorys.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(UserDto body) throws BadRequestException {
        if (userRepository.findByEmail(body.email()).isPresent()) {
            throw new BadRequestException("L'email: " + body.email() + " è già in uso!");
        }
        User user = new User(
                body.nome(),
                body.cognome(),
                body.dataDiNascita(),
                body.email(),
                body.password());

        if (user.getEta()<18){
            throw new UnauthorizedException(body.nome() + "Non puo proseguire con la registrazione perché ancora minorenne!");
        } else {
        return userRepository.save(user);
        }
    }
    public Page<User> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userRepository.findAll(pageable);
    }
    public User findById(UUID id_user) {
        return this.userRepository.findById(id_user).orElseThrow(() -> new NotFoundException(id_user));
    }
    public void findAndDelete(UUID idUser) {
        User found = this.findById(idUser);
        this.userRepository.delete(found);
    }
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("l'utente con " + " " + email + " " + "non è stato trovato!"));
    }
    public User findAndUpdate(UUID idUser, UserDto body) throws BadRequestException {
        if (this.userRepository.findByEmail(body.email()).isPresent()) {
            throw new BadRequestException("L'email: " + body.email() + " è già in uso!");
        }
        User found = this.findById(idUser);
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        found.setPassword(body.password());
        return this.userRepository.save(found);
    }
}
