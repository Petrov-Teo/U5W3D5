package PetrovTodor.Gestione_Eventi_Final.controllers;

import PetrovTodor.Gestione_Eventi_Final.payload.UserLoginDto;
import PetrovTodor.Gestione_Eventi_Final.payload.responsPayload.UserLoginRespoDto;
import PetrovTodor.Gestione_Eventi_Final.services.AuthService;
import PetrovTodor.Gestione_Eventi_Final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public UserLoginRespoDto login(@RequestBody UserLoginDto payload) {
        return new UserLoginRespoDto(this.authService.controlloCredenzialiAndGenerazioneToken(payload));
    }
}
