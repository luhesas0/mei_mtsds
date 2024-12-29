package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name= "utilizadores")
public class Utilizador implements UserDetails {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único

   @Column(name = "nome", nullable = false,length = 100)
    private String nome; //Nome do utilizador

   @Column(name = "email", nullable = false, unique = true,length = 150)
    private String email; //Email unico do utilizador

    @JsonIgnore
   @Column(name = "password", nullable = false, length = 255)
    private String password; //Senha criptografada

   @Column(name = "status", nullable = false, length = 50)
    private Boolean status; //Estado do utilizador (Ativo/Inativo)

    @CreatedDate
    @Column(name = "created_date",nullable = false,updatable = false)
    private LocalDateTime createdDate; //Data de criação (auditoria)

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate; //Data de última modificação

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
        return status;
    }
}
