package PetrovTodor.Gestione_Eventi_Final.controllers;

import PetrovTodor.Gestione_Eventi_Final.exceptions.BadRequestException;
import PetrovTodor.Gestione_Eventi_Final.exceptions.NotFoundException;
import PetrovTodor.Gestione_Eventi_Final.payload.UserDto;
import PetrovTodor.Gestione_Eventi_Final.payload.UserLoginDto;
import PetrovTodor.Gestione_Eventi_Final.payload.responsPayload.UserLoginRespoDto;
import PetrovTodor.Gestione_Eventi_Final.payload.responsPayload.UserResponseDto;
import PetrovTodor.Gestione_Eventi_Final.services.AuthService;
import PetrovTodor.Gestione_Eventi_Final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public UserLoginRespoDto login(@RequestBody UserLoginDto payload) {
        return new UserLoginRespoDto(this.authService.controlloCredenzialiAndGenerazioneToken(payload));
    }

    @PostMapping ("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto save(@RequestBody @Validated UserDto body, BindingResult validationResult) throws NotFoundException, org.apache.coyote.BadRequestException {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        } else {
            return new UserResponseDto(this.userService.save(body).getId_user());
        }
    }


}
