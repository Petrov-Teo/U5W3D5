package PetrovTodor.Gestione_Eventi_Final.controllers;

import PetrovTodor.Gestione_Eventi_Final.entities.Evento;
import PetrovTodor.Gestione_Eventi_Final.entities.Prenotazione;
import PetrovTodor.Gestione_Eventi_Final.entities.User;
import PetrovTodor.Gestione_Eventi_Final.exceptions.BadRequestException;
import PetrovTodor.Gestione_Eventi_Final.exceptions.NotFoundException;
import PetrovTodor.Gestione_Eventi_Final.payload.EventoDto;
import PetrovTodor.Gestione_Eventi_Final.payload.responsPayload.EventoResponseDto;
import PetrovTodor.Gestione_Eventi_Final.services.EventoService;
import PetrovTodor.Gestione_Eventi_Final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    EventoService eventoService;
    @Autowired
    UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')") // SOLO ORGANIZZATORI POSSONO CREARE EVENTI
    public EventoResponseDto saveEvento(@RequestBody @Validated EventoDto body,
                                        BindingResult validationResult,
                                        @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException, BadRequestException {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        }

        return new EventoResponseDto(this.eventoService.saveEvento(body, userDetails.getUsername()).getId_evento());
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public List<Evento> getMyEvents(@AuthenticationPrincipal UserDetails userDetails) {
        // Trova l'utente nel database utilizzando lo username (email)
        User user = userService.findByEmail(userDetails.getUsername());
        return eventoService.findByOrganizzatore(user);
    }

    @GetMapping
    public Page<Evento> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "idDipendente") String sorteBy) {

        return eventoService.findAll(page, size, sorteBy);
    }

    @PutMapping("{idEvento}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento findByIdAndUpdate(@PathVariable UUID idEvento, @RequestParam EventoDto body,@AuthenticationPrincipal UserDetails userDetails) throws BadRequestException, org.apache.coyote.BadRequestException {

        return this.eventoService.findAndUpdate(idEvento, body);
    }

    @DeleteMapping("{idEvento}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(@PathVariable UUID idEvento) {
        eventoService.findAndDelete(idEvento);
    }
}
