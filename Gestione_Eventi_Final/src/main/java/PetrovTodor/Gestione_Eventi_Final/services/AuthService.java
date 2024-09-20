package PetrovTodor.Gestione_Eventi_Final.services;

import PetrovTodor.Gestione_Eventi_Final.entities.User;
import PetrovTodor.Gestione_Eventi_Final.exceptions.UnauthorizedException;
import PetrovTodor.Gestione_Eventi_Final.payload.UserLoginDto;
import PetrovTodor.Gestione_Eventi_Final.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;


    @Autowired
    PasswordEncoder bcrypt;

    public String controlloCredenzialiAndGenerazioneToken(UserLoginDto body) {
        User found = userService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {

            return jwtTools.creatToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }

    }
}
