package PetrovTodor.Gestione_Eventi_Final.security;


import PetrovTodor.Gestione_Eventi_Final.entities.User;
import PetrovTodor.Gestione_Eventi_Final.exceptions.UnauthorizedException;
import PetrovTodor.Gestione_Eventi_Final.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTChekFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
   private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //E' il metodo che verrà richiamato per ogni richiesta, tranne quelle che escludiamo
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Si prega di inserire il token nell'Authorization Header!");
        String accessToken = authHeader.replace("Bearer ", "");
        
        jwtTools.verifyToken(accessToken);
        String id = jwtTools.extractDipendentefromTken(accessToken);
        User userAttuale = this.userService.findById(UUID.fromString(id));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAttuale, null, userAttuale.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);// <-- associo l'utente autenticato al Context

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Ci serve per disabilitare alcune richieste ad esempio su determinati endpoint oppure determinati controller
        // Nel nostro caso non vengano chiamati i token in fase di login e/o registrazione
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
