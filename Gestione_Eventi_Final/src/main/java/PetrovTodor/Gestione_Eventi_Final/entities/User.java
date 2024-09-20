package PetrovTodor.Gestione_Eventi_Final.entities;

import PetrovTodor.Gestione_Eventi_Final.entities.enums.Ruolo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
@Setter
@NoArgsConstructor
@ToString
@Table(name = "utenti")
@JsonIgnoreProperties({"password", "role", "authorities", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "credentialsNonExpired"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id_user;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String email;
    private String password;
    private Ruolo ruolo;

    public User(String nome, String cognome, LocalDate dataDiNascita, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.password = password;
        this.ruolo = Ruolo.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public int getEta() {
        return LocalDate.now().getYear() - this.dataDiNascita.getYear();
    }
}
