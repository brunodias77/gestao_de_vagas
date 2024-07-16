package br.com.brunodias.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration // Indica que esta classe contém configurações de beans do Spring.
@EnableMethodSecurity // Habilita a segurança baseada em anotações nos métodos.
public class SecurityConfig {

    @Autowired // Injeta automaticamente o filtro de segurança da empresa.
    private SecurityCompanyFilter securityCompanyFilter;

    @Autowired // Injeta automaticamente o filtro de segurança do candidato.
    private SecurityCandidateFilter securityCandidateFilter;

    // Define uma lista de endpoints que podem ser acessados por todos sem autenticação.
    private static final String[] PERMIT_ALL_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resource/**",
            "/actuator/**"
    };

    // Configura a cadeia de filtros de segurança do Spring.
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Desabilita a proteção contra CSRF.
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/candidate/").permitAll() // Permite o acesso público às rotas "/candidate/".
                            .requestMatchers("/company/").permitAll() // Permite o acesso público às rotas "/company/".
                            .requestMatchers("/company/auth").permitAll() // Permite o acesso público às rotas "/company/auth".
                            .requestMatchers("/candidate/auth").permitAll() // Permite o acesso público às rotas "/candidate/auth".
                            .requestMatchers(PERMIT_ALL_LIST).permitAll(); // Permite o acesso público às rotas definidas em PERMIT_ALL_LIST.
                    auth.anyRequest().authenticated(); // Exige autenticação para qualquer outra rota.
                })
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class) // Adiciona o filtro de segurança do candidato antes do filtro de autenticação básica.
                .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class); // Adiciona o filtro de segurança da empresa antes do filtro de autenticação básica.

        return http.build(); // Constrói a cadeia de filtros de segurança.
    }

    // Define um bean para o codificador de senhas usando BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Retorna uma instância de BCryptPasswordEncoder para codificação de senhas.
    }
}
