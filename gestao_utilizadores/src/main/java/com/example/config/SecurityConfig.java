package com.example.config;

        import com.example.service.UtilizadorService;
        import com.example.filter.JwtAuthenticationFilter;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.security.web.SecurityFilterChain;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
        import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuração do Spring Security para o microserviço.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UtilizadorService utilizadorService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UtilizadorService utilizadorService, JwtAuthenticationFilter jwtAuthenticationFilter){
        this.utilizadorService = utilizadorService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura o gestor de autenticação
     *
     * @param authenticationConfiguration Configuração de autenticação do Spring.
     * @return AuthenticationManager.
     * @throws Exception Caso ocorra erro na configuração.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura o encoder da password.
     *
     * @return PasswordEncoder para criptografia de passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura a cadeia de filtros de segurança.
     *
     * @param http Configuração de HttpSecurity.
     * @return SecurityFilterChain configurada.
     * @throws Exception Caso ocorra erro na configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //JWT é stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/auth/**")).permitAll()
                        .anyRequest().authenticated() //Qualquer outro endpoint exige autenticação
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //Filtro para validação de JWT
                .build();
    }
}
