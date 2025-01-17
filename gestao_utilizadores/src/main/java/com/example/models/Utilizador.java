package com.example.models;

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

/**
 * Entidade principal que representa os utilizadores do sistema.
 * Implementa UserDetails para integração com Spring Security.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name= "utilizador")
public class Utilizador implements UserDetails {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único do utilizador

   @Column(name = "nome", nullable = false,length = 100)
    private String nome; //Nome completo do utilizador

   @Column(name = "email", nullable = false, unique = true,length = 150)
    private String email; //Email unico do utilizador

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String password; //Password criptografada do utilizador

    @Column(name= "telemovel", nullable = false, length = 15)
    private String telemovel; //Número de telemóvel do utilizador.

    @Column(name = "ativo", nullable = false, length = 15)
    private Boolean ativo;  //Estado do utilizador (Ativo/Inativo)

    @Column(name = "id_unidade", nullable = true)
    private Long unidade; //Identificador da Organização/unidade associada aos utilizador

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_utilizador", nullable = false, length = 20)
    private TipoUtilizador tipoUtilizador; // Tipo do utilizador (Administrador, Funcionário, Cliente)

    @CreatedDate
    @Column(name = "data_criacao",nullable = false,updatable = false)
    private LocalDateTime dataCriacao; //Data de criação (para auditoria)

    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao; //Data de última modificação (para auditoria)

    @ManyToMany
    @JoinTable(
            name = "utilizador_permissoes",
            joinColumns = @JoinColumn(name = "utilizador_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private List<Permissao> permissoes; // Lista de permissões atribuídas ao utilizador

 // Métodos adicionais para gerir permissões

 /**
  * Define uma lista de permissões para o utilizador.
  *
  * @param permissoes Lista de permissões a ser atribuída.
  */
 public void setPermissoes(List<Permissao> permissoes){
  this.permissoes = permissoes;
 }

 /**
  * Adiciona uma permissão específica ao utilizador.
  *
  * @param permissao Permissão a ser adicionada.
  */
 public void addPermissao(Permissao permissao){
  this.permissoes.add(permissao);
 }

 // Métodos obrigatórios para a interface UserDetails

 /**
  * Obtém as permissões (autoridades) atribuídas ao utilizador.
  *
  * @return Coleção de autoridades do utilizador.
  */
@Override
public Collection<? extends GrantedAuthority> getAuthorities(){
 return List.of(new SimpleGrantedAuthority("ROLE_"+ tipoUtilizador.name()));
}

 /**
  * Obtém a password do utilizador.
  *
  * @return Password do utilizador.
  */
 @Override
 public String getPassword(){
  return password;
 }

 /**
  * Obtém o nome de utilizador (neste caso, o email).
  *
  * @return Email do utilizador.
  */
 @Override
 public String getUsername(){
  return email;
 }

 /**
  * Indica se a conta do utilizador não expirou.
  *
  * @return Verdadeiro ser a conta não expirou.
  */
 @Override
 public boolean isAccountNonExpired(){
  return true; //Conta nunca expira
 }

 /**
  * Indica se a conta do utilizador não está bloqueada.
  *
  * @return Verdadeiro se a conta não está bloqueada.
  */
 @Override
 public boolean isAccountNonLocked(){
  return true; //Conta nunca é bloqueada
 }

 /**
  * Indica se as credenciais do utilizador não expiraram.
  *
  * @return Verdadeiro se as credenciais não expiraram.
  */
 @Override
 public boolean isCredentialsNonExpired(){
  return true; //Credenciais nunca expiram
 }

 /**
  * Indica se o utilizador está habilitado (ativo).
  *
  * @return Verdadeiro se o utilizador está ativo.
  */
 @Override
 public boolean isEnabled(){
  return ativo; //Ativo determina se o utilizador está habilitado
 }

 //Construtores adicionais

 /**
  * Construtor para criar um utilizador.
  *
  * @param nome Nome do utilizador.
  * @param email  Email único do utilizador.
  * @param password Password criptografada.
  * @param telemovel Número de telemóvel.
  * @param tipoUtilizador  Tipo do utilizador (Admin, Cliente, Funcionario).
  */
 public Utilizador(String nome, String email, String password, String telemovel,TipoUtilizador tipoUtilizador){
  this.nome = nome;
  this.email = email;
  this.password = password;
  this.telemovel = telemovel;
  this.ativo = true; //Por padrão, novos utilizadores estão ativos
  this.tipoUtilizador = tipoUtilizador;
 }
}
