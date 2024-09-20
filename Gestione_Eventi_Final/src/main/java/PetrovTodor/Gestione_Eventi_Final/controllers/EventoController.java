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

    @GetMapping
    public Page<Evento> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "idEvento") String sorteBy) {

        return eventoService.findAll(page, size, sorteBy);
    }

    @GetMapping("/{idEvento}")
    public Evento findById(@PathVariable UUID idEvento) {
        return eventoService.findById(idEvento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'ADMIN')") // SOLO ORGANIZZATORI E ADMIN POSSONO CREARE EVENTI
    public EventoResponseDto saveEvento(@RequestBody @Validated EventoDto body,
                                        BindingResult validationResult,
                                        @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException, BadRequestException {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        }

        return new EventoResponseDto(this.eventoService.saveEvento(body, userDetails.getUsername()).getIdEvento());
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'ADMIN')")
    public List<Evento> getMyEvents(@AuthenticationPrincipal UserDetails userDetails) {
        // Trova l'utente nel database utilizzando lo username (email)
        User user = userService.findByEmail(userDetails.getUsername());
        return eventoService.findByOrganizzatore(user);
    }

    @PutMapping("{idEvento}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'ADMIN')")
    public Evento findByIdAndUpdate(@PathVariable UUID idEvento, @RequestParam EventoDto body,@AuthenticationPrincipal UserDetails userDetails) throws BadRequestException, org.apache.coyote.BadRequestException {

        return this.eventoService.findAndUpdate(idEvento, body);
    }

    @DeleteMapping("{idEvento}")
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE', 'ADMIN')") // SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(@PathVariable UUID idEvento) {
        eventoService.findAndDelete(idEvento);
    }
}
