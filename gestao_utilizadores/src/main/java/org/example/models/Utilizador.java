package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "utilizador")
public class Utilizador implements UserDetails {

   @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_utilizador", unique = true, nullable = false)
    private String id; //Identificador único

   @Column(name = "nome", nullable = false,length = 255)
    private String nome; //Nome do utilizador

   @Column(name = "email", nullable = false, unique = true,length = 255)
    private String email; //Email unico do utilizador

    @JsonIgnore
   @Column(name = "password", nullable = false, length = 255)
    private String password; //Senha criptografada

   @Column(name = "status", nullable = false, length = 50)
    private String status; //Estado do utilizador (Ativo, Inativo)

    @Column(name = "data_criacao",nullable = false,updatable = false)
    private LocalDateTime data_criacao; //Data de criação (auditoria)

    @Column(name = "data_atualizacao")
    private LocalDateTime data_atualizacao; //Data de última atualização (auditoria)

    //Relacionamento com roles
    @OneToMany(mappedBy = "utilizador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UtilizadoresRoles> utilizadoresRoles;

    //Relacionamento com logins
    @OneToMany(mappedBy = "utilizador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RegistoLogins> registologins;

    //Métodos de UserDetails para Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return utilizadoresRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getNome().toUpperCase()))
                .toList();
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
       return true;
    }

    @Override
    public boolean isEnabled(){
        return "Ativo".equalsIgnoreCase(status);
    }
}
