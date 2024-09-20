package PetrovTodor.Gestione_Eventi_Final.controllers;

import PetrovTodor.Gestione_Eventi_Final.entities.User;
import PetrovTodor.Gestione_Eventi_Final.exceptions.BadRequestException;
import PetrovTodor.Gestione_Eventi_Final.exceptions.NotFoundException;
import PetrovTodor.Gestione_Eventi_Final.payload.UserDto;
import PetrovTodor.Gestione_Eventi_Final.payload.responsPayload.UserResponseDto;
import PetrovTodor.Gestione_Eventi_Final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    public UserResponseDto saveDipendente(@RequestBody @Validated UserDto body, BindingResult validationResult) throws NotFoundException, org.apache.coyote.BadRequestException {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        } else {
            return new UserResponseDto(this.userService.save(body).getId_user());
        }
    }

    @GetMapping("/me")
    // Permette di prendere le info sul proprio profilo (identifica chi si è loggato, permettendo di effettuare anche delle modifiche)
    public User getProfile(@AuthenticationPrincipal User currentDipendente) {
        return currentDipendente;
    }

    @PutMapping("/me")
    // Con questo metodo abbiamo permesso all'utente di modificare il proprio profilo, ma non quello degli altri, che
    //potranno essere modificati solo dagli amministratori!
    public User putProfileDipendente(@AuthenticationPrincipal User currentDipendente, @RequestBody UserDto body) throws BadRequestException, org.apache.coyote.BadRequestException {
        return this.userService.findAndUpdate(currentDipendente.getId_user(), body);
    }
    @DeleteMapping("/me")
    // serve per eliminare l proprio profilo, in caso si decida, ma non quello degli altri, che potrà essere eliminato
    //dagli amministratori
    public void deleteDipendenteMe(@AuthenticationPrincipal User currentUSer) {
        this.deleteDipendente(currentUSer.getId_user());
    }

    @DeleteMapping("{idDipendente}")
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(@PathVariable UUID idUser) {
        userService.findAndDelete(idUser);
    }
}
